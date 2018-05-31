package de.DrP3pp3r.wot.mm_sim;

import java.util.List;

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
        
        for(TankUse u : tankUses)
        {
        	System.out.println(u.toString());
        }
        
        TankUsage tankUsage = new TankUsage();
        tankUsage.setTankUses(tankUses);
        
        TankTypeSelector tankTypeSelector = tankUsage.buildTankTypeSelector();
        TankType selectedTankType = tankTypeSelector.getTankType(0.5);
        System.out.format("The selected tank type is '%s.\n", selectedTankType.getName());
        
        for(int i = 0; i < 10; ++i)
        {
        	selectedTankType = tankTypeSelector.getRandomTankType();
            System.out.format("The selected tank type is '%s.\n", selectedTankType.getName());	
        }
        
    }
}
