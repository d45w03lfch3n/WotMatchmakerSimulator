package de.DrP3pp3r.wot.mm_sim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import de.DrP3pp3r.wot.mm_sim.database.Database;
import de.DrP3pp3r.wot.mm_sim.tanks.TankClass;
import de.DrP3pp3r.wot.mm_sim.tanks.TankType;
import de.DrP3pp3r.wot.mm_sim.tanks.TankTypeSelector;
import de.DrP3pp3r.wot.mm_sim.tanks.TankUsage;
import de.DrP3pp3r.wot.mm_sim.tanks.TankUse;

public class MatchmakerSimulation 
{
    public static void main( String[] args )
    {
        System.out.println( "MatchmakerSimulation was started!" );
        
        Database database = new Database();
        List<TankType> tankTypes = database.execute(
        		(Session s) ->
        		{ 
        			String hql = "from TankType";
        			Query<TankType> query = s.createQuery(hql);
        			return query.list(); 
        		});
        
        System.out.format("Number of tank types: '%d'.\n", tankTypes.size());
        
        for(TankType t : tankTypes)
        {
        	System.out.println(t.toString());
        }
        
        List<TankUse> tankUses = database.execute(
        		(Session s) ->
        		{ 
        			String hql = "from TankUse";
        			Query<TankUse> query = s.createQuery(hql);
        			return query.list(); 
        		});
        
        TankUsage tankUsage = new TankUsage();
        tankUsage.setTankUses(tankUses);
        System.out.print(tankUsage.toString());
        
        TankTypeSelector tankTypeSelector = tankUsage.buildTankTypeSelector();
        TankType selectedTankType = null;
//        TankType selectedTankType = tankTypeSelector.getTankType(0.5);
//        System.out.format("The selected tank type is '%s.\n", selectedTankType.getName());
        
        System.out.format("Initializing counter map.\n");
        Map<String, Integer> counterMap = new HashMap<String, Integer>();
        for(TankType t : tankTypes)
        {
        	counterMap.put(t.getName(), 0);
        }

        System.out.format("Selecting tanks.\n");
        Integer tankCount = 100000;
        for(int i = 0; i < tankCount; ++i)
        {
        	selectedTankType = tankTypeSelector.getRandomTankType();
        	counterMap.put(selectedTankType.getName(), counterMap.get(selectedTankType.getName()) + 1);
        }
        
        System.out.format("Tank appearances:\n");
        for(Map.Entry<String, Integer> count : counterMap.entrySet())
        {
        	System.out.format("Appearance of tank '%s' is %.4f%%.\n", count.getKey(), (double)count.getValue()/tankCount*100);
        }
    }
}
