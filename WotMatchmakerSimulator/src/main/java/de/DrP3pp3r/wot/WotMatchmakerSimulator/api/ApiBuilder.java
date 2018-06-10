package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.database.Database;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankTypeSelector;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankUsage;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankUse;

public class ApiBuilder
{
	public static API buildApi(Database database)
	{
		List<TankType> tankTypes = getTankTypes(database);
		List<TankUse> tankUses = getTankUses(database);
		TankUsage tankUsage = buildTankUsage(tankUses);
		TankTypeSelector tankTypeSelector = tankUsage.buildTankTypeSelector();
		
		testTankTypeSelector(tankTypes, tankTypeSelector);
		
		API api = new API(database, tankTypeSelector);
		
		return api;
	}
	
	private static List<TankType> getTankTypes(Database database)
	{
		List<TankType> tankTypes = database.execute(
				(Session s) ->
				{
					String hql = "from TankType";
					@SuppressWarnings("unchecked")
					Query<TankType> query = s.createQuery(hql);
					return query.list();
				});

//		System.out.format("Number of tank types: '%d'.\n", tankTypes.size());

//		for(TankType t : tankTypes)
//		{
//			System.out.println(t.toString());
//		}
		
		return tankTypes;
	}
	
	private static List<TankUse> getTankUses(Database database)
	{
		List<TankUse> tankUses = database.execute(
				(Session s) ->
				{
					String hql = "from TankUse";
					@SuppressWarnings("unchecked")
					Query<TankUse> query = s.createQuery(hql);
					return query.list();
				});
		
		return tankUses;
	}
	
	private static TankUsage buildTankUsage(List<TankUse> tankUses)
	{
		TankUsage tankUsage = new TankUsage();
		tankUsage.setTankUses(tankUses);
		
		// System.out.print(tankUsage.toString());
		
		return tankUsage;
	}
	
	private static void testTankTypeSelector(List<TankType> tankTypes, TankTypeSelector tankTypeSelector)
	{
		TankType selectedTankType = null;
		// TankType selectedTankType = tankTypeSelector.getTankType(0.5);
		// System.out.format("The selected tank type is '%s.\n",
		// selectedTankType.getName());

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
			System.out.format("Appearance of tank '%s' is %.4f%%.\n", count.getKey(),
					(double) count.getValue() / tankCount * 100);
		}
	}
}
