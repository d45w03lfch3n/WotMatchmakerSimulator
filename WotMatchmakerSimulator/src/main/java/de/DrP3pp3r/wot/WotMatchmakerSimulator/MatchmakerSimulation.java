package de.DrP3pp3r.wot.WotMatchmakerSimulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.API;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.database.Database;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.ExampleMatchmaker;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankClass;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankTypeSelector;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankUsage;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankUse;

public class MatchmakerSimulation 
{
    public static void main( String[] args )
    {
        System.out.println( "MatchmakerSimulation was started!" );
        
        Boolean optionCreateTankTypes = false;
        Boolean optionTankTypeSelectionTest = false;
        Boolean optionMatchmaker = true;
        
        Database database = new Database();
        
        if(optionCreateTankTypes)
        {
        	createTankTypesAndTankUses(database);
        	return;
        }
                
        List<TankType> tankTypes = database.execute(
        		(Session s) ->
        		{ 
        			String hql = "from TankType";
        			@SuppressWarnings("unchecked")
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
        			@SuppressWarnings("unchecked")
        			Query<TankUse> query = s.createQuery(hql);
        			return query.list(); 
        		});
        
        TankUsage tankUsage = new TankUsage();
        tankUsage.setTankUses(tankUses);
        System.out.print(tankUsage.toString());
        
        TankTypeSelector tankTypeSelector = tankUsage.buildTankTypeSelector();
        
        if(optionTankTypeSelectionTest)
        {
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
        
        if(optionMatchmaker)
        {
	        API api = new API(database, tankTypeSelector);
	        
	        ExampleMatchmaker mm = new ExampleMatchmaker();
	        mm.setApi(api);
	        mm.start();
	        
	        try
	        {
				Thread.sleep(60000);
			}
	        catch (InterruptedException e)
	        {
			}
	        
	        mm.stop();
	        
	        System.out.format("Matchmaker created '%d' matches and matched '%d' tanks.\n", mm.getFinishedMatches(), mm.getMatchedTanks());
        }
    }
    
    private static void createTankTypesAndTankUses(Database database)
    {
    	createTankTypeAndUses(database, "Renault NC-31", TankClass.LIGHT_TANK, 1, 1, 2, 1003);
    	createTankTypeAndUses(database, "Kolohousenka", TankClass.LIGHT_TANK, 1, 1, 2, 1370);
    	createTankTypeAndUses(database, "Renault FT", TankClass.LIGHT_TANK, 1, 1, 2, 1182);
    	createTankTypeAndUses(database, "Leichttraktor", TankClass.LIGHT_TANK, 1, 1, 2, 6726);
    	createTankTypeAndUses(database, "Fiat 3000", TankClass.LIGHT_TANK, 1, 1, 2, 20072);
    	createTankTypeAndUses(database, "Renault Otsu", TankClass.LIGHT_TANK, 1, 1, 2, 1781);
    	createTankTypeAndUses(database, "Strv fm/21", TankClass.LIGHT_TANK, 1, 1, 2, 1736);
    	createTankTypeAndUses(database, "T1 Cunningham", TankClass.LIGHT_TANK, 1, 1, 2, 2774);
    	createTankTypeAndUses(database, "MS-1", TankClass.LIGHT_TANK, 1, 1, 2, 2686);
    	createTankTypeAndUses(database, "Vickers Medium", TankClass.MEDIUM_TANK, 1, 1, 2, 2676);
    	createTankTypeAndUses(database, "Vickers Mk. E Type B", TankClass.LIGHT_TANK, 2, 2, 3, 1128);
    	createTankTypeAndUses(database, "LT vz. 35", TankClass.LIGHT_TANK, 2, 2, 3, 1133);
    	createTankTypeAndUses(database, "Renault R35", TankClass.LIGHT_TANK, 2, 2, 3, 518);
    	createTankTypeAndUses(database, "Hotchkiss H35", TankClass.LIGHT_TANK, 2, 2, 3, 1585);
    	createTankTypeAndUses(database, "FCM 36", TankClass.LIGHT_TANK, 2, 2, 3, 732);
    	createTankTypeAndUses(database, "D1", TankClass.LIGHT_TANK, 2, 2, 3, 780);
    	createTankTypeAndUses(database, "Pz.Kpfw. II Ausf. D", TankClass.LIGHT_TANK, 2, 2, 3, 824);
    	createTankTypeAndUses(database, "Pz.Kpfw. II", TankClass.LIGHT_TANK, 2, 2, 3, 5529);
    	createTankTypeAndUses(database, "Pz.Kpfw. I", TankClass.LIGHT_TANK, 2, 2, 2, 1198);
    	createTankTypeAndUses(database, "Pz.Kpfw. 38H 735 (f)", TankClass.LIGHT_TANK, 2, 2, 3, 695);
    	createTankTypeAndUses(database, "Pz.Kpfw. 35 (t)", TankClass.LIGHT_TANK, 2, 2, 3, 2369);
    	createTankTypeAndUses(database, "L6/40", TankClass.LIGHT_TANK, 2, 2, 3, 7431);
    	createTankTypeAndUses(database, "Type 97 Te-Ke", TankClass.LIGHT_TANK, 2, 2, 3, 298);
    	createTankTypeAndUses(database, "Type 95 Ha-Go", TankClass.LIGHT_TANK, 2, 2, 3, 352);
    	createTankTypeAndUses(database, "Strv m/38", TankClass.LIGHT_TANK, 2, 2, 3, 1643);
    	createTankTypeAndUses(database, "L-60", TankClass.LIGHT_TANK, 2, 2, 3, 1896);
    	createTankTypeAndUses(database, "M2", TankClass.LIGHT_TANK, 2, 2, 3, 1015);
    	createTankTypeAndUses(database, "Light Mk. VIC", TankClass.LIGHT_TANK, 2, 2, 2, 941);
    	createTankTypeAndUses(database, "Cruiser Mk. III", TankClass.LIGHT_TANK, 2, 2, 3, 3510);
    	createTankTypeAndUses(database, "Cruiser Mk. I", TankClass.LIGHT_TANK, 2, 2, 3, 1157);
    	createTankTypeAndUses(database, "T7 Combat Car", TankClass.LIGHT_TANK, 2, 2, 2, 1638);
    	createTankTypeAndUses(database, "T2 Light Tank", TankClass.LIGHT_TANK, 2, 2, 3, 2045);
    	createTankTypeAndUses(database, "T1E6", TankClass.LIGHT_TANK, 2, 2, 3, 911);
    	createTankTypeAndUses(database, "M2 Light Tank", TankClass.LIGHT_TANK, 2, 2, 3, 6798);
    	createTankTypeAndUses(database, "Tetrarch", TankClass.LIGHT_TANK, 2, 2, 3, 1349);
    	createTankTypeAndUses(database, "T-60", TankClass.LIGHT_TANK, 2, 2, 3, 1177);
    	createTankTypeAndUses(database, "T-45", TankClass.LIGHT_TANK, 2, 2, 3, 1544);
    	createTankTypeAndUses(database, "T-26", TankClass.LIGHT_TANK, 2, 2, 3, 2372);
    	createTankTypeAndUses(database, "BT-2", TankClass.LIGHT_TANK, 2, 2, 3, 2233);
    	createTankTypeAndUses(database, "M14/41", TankClass.MEDIUM_TANK, 2, 2, 3, 16919);
    	createTankTypeAndUses(database, "Type 89 I-Go/Chi-Ro", TankClass.MEDIUM_TANK, 2, 2, 3, 1456);
    	createTankTypeAndUses(database, "Chi-Ni", TankClass.MEDIUM_TANK, 2, 2, 3, 474);
    	createTankTypeAndUses(database, "Vickers Medium Mk. II", TankClass.MEDIUM_TANK, 2, 2, 3, 1359);
    	createTankTypeAndUses(database, "T2 Medium Tank", TankClass.MEDIUM_TANK, 2, 2, 3, 4214);
    	createTankTypeAndUses(database, "Renault FT 75 BS", TankClass.SELF_PROPELLED_GUN, 2, 2, 3, 1113);
    	createTankTypeAndUses(database, "G.Pz. Mk. VI (e)", TankClass.SELF_PROPELLED_GUN, 2, 2, 3, 2429);
    	createTankTypeAndUses(database, "Loyd Gun Carriage", TankClass.SELF_PROPELLED_GUN, 2, 2, 3, 1139);
    	createTankTypeAndUses(database, "T1 HMC", TankClass.SELF_PROPELLED_GUN, 2, 2, 3, 1568);
    	createTankTypeAndUses(database, "SU-18", TankClass.SELF_PROPELLED_GUN, 2, 2, 3, 1805);
    	createTankTypeAndUses(database, "T-26G FT", TankClass.TANK_DESTROYER, 2, 2, 3, 947);
    	createTankTypeAndUses(database, "Renault FT AC", TankClass.TANK_DESTROYER, 2, 2, 3, 599);
    	createTankTypeAndUses(database, "Panzerjager I", TankClass.TANK_DESTROYER, 2, 2, 3, 3883);
    	createTankTypeAndUses(database, "Pvlvv fm/42", TankClass.TANK_DESTROYER, 2, 2, 3, 1658);
    	createTankTypeAndUses(database, "Universal Carrier 2-pdr", TankClass.TANK_DESTROYER, 2, 2, 3, 1023);
    	createTankTypeAndUses(database, "T3 HMC", TankClass.TANK_DESTROYER, 2, 2, 3, 1703);
    	createTankTypeAndUses(database, "AT-1", TankClass.TANK_DESTROYER, 2, 2, 3, 2104);
    	createTankTypeAndUses(database, "Type 91 Heavy", TankClass.HEAVY_TANK, 3, 3, 4, 3001);
    	createTankTypeAndUses(database, "Type 2597 Chi-Ha", TankClass.LIGHT_TANK, 3, 3, 4, 1851);
    	createTankTypeAndUses(database, "LT vz. 38", TankClass.LIGHT_TANK, 3, 3, 4, 2930);
    	createTankTypeAndUses(database, "AMX 38", TankClass.LIGHT_TANK, 3, 3, 4, 2303);
    	createTankTypeAndUses(database, "Pz.Kpfw. T 15", TankClass.LIGHT_TANK, 3, 3, 4, 1663);
    	createTankTypeAndUses(database, "Pz.Kpfw. III Ausf. E", TankClass.LIGHT_TANK, 3, 3, 4, 7303);
    	createTankTypeAndUses(database, "Pz.Kpfw. II Ausf. J", TankClass.LIGHT_TANK, 3, 3, 4, 525);
    	createTankTypeAndUses(database, "Pz.Kpfw. II Ausf. G", TankClass.LIGHT_TANK, 3, 3, 4, 2423);
    	createTankTypeAndUses(database, "Pz.Kpfw. I Ausf. C", TankClass.LIGHT_TANK, 3, 3, 4, 16125);
    	createTankTypeAndUses(database, "Pz.Kpfw. 38 (t)", TankClass.LIGHT_TANK, 3, 3, 4, 8614);
    	createTankTypeAndUses(database, "43 M. Toldi III", TankClass.LIGHT_TANK, 3, 3, 4, 1506);
    	createTankTypeAndUses(database, "Type 98 Ke-Ni", TankClass.LIGHT_TANK, 3, 3, 4, 571);
    	createTankTypeAndUses(database, "Strv m/40L", TankClass.LIGHT_TANK, 3, 3, 4, 3144);
    	createTankTypeAndUses(database, "Stuart I-IV", TankClass.LIGHT_TANK, 3, 3, 4, 784);
    	createTankTypeAndUses(database, "Cruiser Mk. IV", TankClass.LIGHT_TANK, 3, 3, 4, 4873);
    	createTankTypeAndUses(database, "Cruiser Mk. II", TankClass.LIGHT_TANK, 3, 3, 4, 3436);
    	createTankTypeAndUses(database, "MTLS-1G14", TankClass.LIGHT_TANK, 3, 3, 4, 15511);
    	createTankTypeAndUses(database, "M3 Stuart", TankClass.LIGHT_TANK, 3, 3, 4, 3892);
    	createTankTypeAndUses(database, "M22 Locust", TankClass.LIGHT_TANK, 3, 3, 4, 2589);
    	createTankTypeAndUses(database, "T-70", TankClass.LIGHT_TANK, 3, 3, 4, 2419);
    	createTankTypeAndUses(database, "T-46", TankClass.LIGHT_TANK, 3, 3, 4, 6332);
    	createTankTypeAndUses(database, "T-127", TankClass.LIGHT_TANK, 3, 3, 4, 6649);
    	createTankTypeAndUses(database, "M3 Light", TankClass.LIGHT_TANK, 3, 3, 4, 144);
    	createTankTypeAndUses(database, "LTP", TankClass.LIGHT_TANK, 3, 3, 4, 2059);
    	createTankTypeAndUses(database, "BT-SV", TankClass.LIGHT_TANK, 3, 3, 4, 151);
    	createTankTypeAndUses(database, "BT-7 artillery", TankClass.LIGHT_TANK, 3, 3, 4, 3366);
    	createTankTypeAndUses(database, "BT-7", TankClass.LIGHT_TANK, 3, 3, 4, 3713);
    	createTankTypeAndUses(database, "Somua S35", TankClass.MEDIUM_TANK, 3, 3, 4, 1488);
    	createTankTypeAndUses(database, "D2", TankClass.MEDIUM_TANK, 3, 3, 4, 1566);
    	createTankTypeAndUses(database, "Pz.Kpfw. S35 739 (f)", TankClass.MEDIUM_TANK, 3, 3, 4, 3685);
    	createTankTypeAndUses(database, "Pz.Kpfw. IV Ausf. A", TankClass.MEDIUM_TANK, 3, 3, 4, 2187);
    	createTankTypeAndUses(database, "Grosstraktor - Krupp", TankClass.MEDIUM_TANK, 3, 3, 4, 217);
    	createTankTypeAndUses(database, "M15/42", TankClass.MEDIUM_TANK, 3, 3, 4, 45567);
    	createTankTypeAndUses(database, "Type 97 Chi-Ha", TankClass.MEDIUM_TANK, 3, 3, 4, 1040);
    	createTankTypeAndUses(database, "Vickers Medium Mk. III", TankClass.MEDIUM_TANK, 3, 3, 4, 2460);
    	createTankTypeAndUses(database, "M2 Medium Tank", TankClass.MEDIUM_TANK, 3, 3, 4, 19584);
    	createTankTypeAndUses(database, "T-29", TankClass.MEDIUM_TANK, 3, 3, 4, 23854);
    	createTankTypeAndUses(database, "Lorraine 39L AM", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 1781);
    	createTankTypeAndUses(database, "Wespe", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 3910);
    	createTankTypeAndUses(database, "Sturmpanzer I Bison", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 1736);
    	createTankTypeAndUses(database, "Sexton II", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 2136);
    	createTankTypeAndUses(database, "Sexton I", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 286);
    	createTankTypeAndUses(database, "T18 HMC", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 1049);
    	createTankTypeAndUses(database, "M7 Priest", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 3150);
    	createTankTypeAndUses(database, "SU-26", TankClass.SELF_PROPELLED_GUN, 3, 3, 4, 3419);
    	createTankTypeAndUses(database, "M3G FT", TankClass.TANK_DESTROYER, 3, 3, 4, 2100);
    	createTankTypeAndUses(database, "Renault UE 57", TankClass.TANK_DESTROYER, 3, 3, 4, 1204);
    	createTankTypeAndUses(database, "FCM 36 Pak 40", TankClass.TANK_DESTROYER, 3, 3, 4, 2364);
    	createTankTypeAndUses(database, "Marder II", TankClass.TANK_DESTROYER, 3, 3, 4, 7223);
    	createTankTypeAndUses(database, "Ikv 72", TankClass.TANK_DESTROYER, 3, 3, 4, 5191);
    	createTankTypeAndUses(database, "Valentine AT", TankClass.TANK_DESTROYER, 3, 3, 4, 1875);
    	createTankTypeAndUses(database, "T56 GMC", TankClass.TANK_DESTROYER, 3, 3, 4, 3628);
    	createTankTypeAndUses(database, "SU-76M", TankClass.TANK_DESTROYER, 3, 3, 4, 5487);
    	createTankTypeAndUses(database, "SU-76I", TankClass.TANK_DESTROYER, 3, 3, 4, 39);
    	createTankTypeAndUses(database, "B1", TankClass.HEAVY_TANK, 4, 4, 5, 4664);
    	createTankTypeAndUses(database, "Pz.Kpfw. B2 740 (f)", TankClass.HEAVY_TANK, 4, 4, 4, 5602);
    	createTankTypeAndUses(database, "Durchbruchswagen 2", TankClass.HEAVY_TANK, 4, 4, 5, 7482);
    	createTankTypeAndUses(database, "Type 95 Heavy", TankClass.HEAVY_TANK, 4, 4, 5, 7811);
    	createTankTypeAndUses(database, "M5A1 Stuart", TankClass.LIGHT_TANK, 4, 4, 6, 8436);
    	createTankTypeAndUses(database, "AMX 40", TankClass.LIGHT_TANK, 4, 4, 6, 7297);
    	createTankTypeAndUses(database, "Pz.Kpfw. II Luchs", TankClass.LIGHT_TANK, 4, 4, 6, 28740);
    	createTankTypeAndUses(database, "Pz.Kpfw. 38 (t) n.A.", TankClass.LIGHT_TANK, 4, 4, 6, 14919);
    	createTankTypeAndUses(database, "Type 5 Ke-Ho", TankClass.LIGHT_TANK, 4, 4, 6, 1801);
    	createTankTypeAndUses(database, "Valentine", TankClass.LIGHT_TANK, 4, 4, 6, 4523);
    	createTankTypeAndUses(database, "Covenanter", TankClass.LIGHT_TANK, 4, 4, 6, 10826);
    	createTankTypeAndUses(database, "M5 Stuart", TankClass.LIGHT_TANK, 4, 4, 6, 7567);
    	createTankTypeAndUses(database, "Valentine II", TankClass.LIGHT_TANK, 4, 4, 4, 6327);
    	createTankTypeAndUses(database, "T-80", TankClass.LIGHT_TANK, 4, 4, 6, 7102);
    	createTankTypeAndUses(database, "A-20", TankClass.LIGHT_TANK, 4, 4, 6, 9233);
    	createTankTypeAndUses(database, "ST vz. 39", TankClass.MEDIUM_TANK, 4, 4, 6, 5343);
    	createTankTypeAndUses(database, "SARL 42", TankClass.MEDIUM_TANK, 4, 4, 6, 1876);
    	createTankTypeAndUses(database, "VK 20.01 (D)", TankClass.MEDIUM_TANK, 4, 4, 6, 1620);
    	createTankTypeAndUses(database, "Pz.Kpfw. IV Ausf. D", TankClass.MEDIUM_TANK, 4, 4, 6, 5512);
    	createTankTypeAndUses(database, "Pz.Kpfw. III Ausf. J", TankClass.MEDIUM_TANK, 4, 4, 6, 4627);
    	createTankTypeAndUses(database, "P26/40", TankClass.MEDIUM_TANK, 4, 4, 6, 101912);
    	createTankTypeAndUses(database, "Type 1 Chi-He", TankClass.MEDIUM_TANK, 4, 4, 6, 1893);
    	createTankTypeAndUses(database, "Lago", TankClass.MEDIUM_TANK, 4, 4, 6, 8035);
    	createTankTypeAndUses(database, "Matilda", TankClass.MEDIUM_TANK, 4, 4, 6, 20952);
    	createTankTypeAndUses(database, "Grant", TankClass.MEDIUM_TANK, 4, 4, 6, 1686);
    	createTankTypeAndUses(database, "AC 1 Sentinel", TankClass.MEDIUM_TANK, 4, 4, 6, 1256);
    	createTankTypeAndUses(database, "M3 Lee", TankClass.MEDIUM_TANK, 4, 4, 6, 17389);
    	createTankTypeAndUses(database, "T-28E with F-30", TankClass.MEDIUM_TANK, 4, 4, 6, 4422);
    	createTankTypeAndUses(database, "T-28", TankClass.MEDIUM_TANK, 4, 4, 6, 20760);
    	createTankTypeAndUses(database, "A-32", TankClass.MEDIUM_TANK, 4, 4, 4, 14);
    	createTankTypeAndUses(database, "AMX 105 AM mle. 47", TankClass.SELF_PROPELLED_GUN, 4, 4, 6, 5556);
    	createTankTypeAndUses(database, "Sturmpanzer II", TankClass.SELF_PROPELLED_GUN, 4, 4, 6, 5034);
    	createTankTypeAndUses(database, "Pz.Sfl. IVb", TankClass.SELF_PROPELLED_GUN, 4, 4, 6, 8555);
    	createTankTypeAndUses(database, "Birch Gun", TankClass.SELF_PROPELLED_GUN, 4, 4, 6, 4294);
    	createTankTypeAndUses(database, "T82 HMC", TankClass.SELF_PROPELLED_GUN, 4, 4, 6, 2573);
    	createTankTypeAndUses(database, "M37", TankClass.SELF_PROPELLED_GUN, 4, 4, 6, 6579);
    	createTankTypeAndUses(database, "SU-5", TankClass.SELF_PROPELLED_GUN, 4, 4, 6, 5483);
    	createTankTypeAndUses(database, "SU-76G FT", TankClass.TANK_DESTROYER, 4, 4, 6, 5695);
    	createTankTypeAndUses(database, "Somua SAu 40", TankClass.TANK_DESTROYER, 4, 4, 6, 2007);
    	createTankTypeAndUses(database, "StuG III Ausf. B", TankClass.TANK_DESTROYER, 4, 4, 6, 3704);
    	createTankTypeAndUses(database, "Marder 38T", TankClass.TANK_DESTROYER, 4, 4, 6, 12278);
    	createTankTypeAndUses(database, "Jagdpanzer 38(t) Hetzer", TankClass.TANK_DESTROYER, 4, 4, 6, 27895);
    	createTankTypeAndUses(database, "Sav m/43", TankClass.TANK_DESTROYER, 4, 4, 6, 11080);
    	createTankTypeAndUses(database, "Alecto", TankClass.TANK_DESTROYER, 4, 4, 6, 4134);
    	createTankTypeAndUses(database, "T40", TankClass.TANK_DESTROYER, 4, 4, 6, 7103);
    	createTankTypeAndUses(database, "M8A1", TankClass.TANK_DESTROYER, 4, 4, 6, 10472);
    	createTankTypeAndUses(database, "SU-85B", TankClass.TANK_DESTROYER, 4, 4, 6, 16575);
    	createTankTypeAndUses(database, "BDR G1 B", TankClass.HEAVY_TANK, 5, 5, 7, 9051);
    	createTankTypeAndUses(database, "VK 30.01 (H)", TankClass.HEAVY_TANK, 5, 5, 7, 13743);
    	createTankTypeAndUses(database, "O-I Experimental", TankClass.HEAVY_TANK, 5, 5, 7, 16206);
    	createTankTypeAndUses(database, "Excelsior", TankClass.HEAVY_TANK, 5, 5, 6, 7558);
    	createTankTypeAndUses(database, "Churchill I", TankClass.HEAVY_TANK, 5, 5, 7, 13401);
    	createTankTypeAndUses(database, "T14", TankClass.HEAVY_TANK, 5, 5, 6, 3081);
    	createTankTypeAndUses(database, "T1 Heavy", TankClass.HEAVY_TANK, 5, 5, 7, 14513);
    	createTankTypeAndUses(database, "KV-220-2 Beta Test", TankClass.HEAVY_TANK, 5, 5, 6, 1);
    	createTankTypeAndUses(database, "KV-220-2", TankClass.HEAVY_TANK, 5, 5, 6, 1454);
    	createTankTypeAndUses(database, "KV-1S", TankClass.HEAVY_TANK, 5, 5, 7, 24281);
    	createTankTypeAndUses(database, "KV-1", TankClass.HEAVY_TANK, 5, 5, 7, 45060);
    	createTankTypeAndUses(database, "Churchill III", TankClass.HEAVY_TANK, 5, 5, 6, 14615);
    	createTankTypeAndUses(database, "AMX ELC bis", TankClass.LIGHT_TANK, 5, 5, 7, 27615);
    	createTankTypeAndUses(database, "VK 16.02 Leopard", TankClass.LIGHT_TANK, 5, 5, 7, 23051);
    	createTankTypeAndUses(database, "M7", TankClass.LIGHT_TANK, 5, 5, 7, 6642);
    	createTankTypeAndUses(database, "M24 Chaffee", TankClass.LIGHT_TANK, 5, 5, 7, 13007);
    	createTankTypeAndUses(database, "T-50", TankClass.LIGHT_TANK, 5, 5, 7, 7232);
    	createTankTypeAndUses(database, "Type T-34", TankClass.MEDIUM_TANK, 5, 5, 7, 12703);
    	createTankTypeAndUses(database, "Skoda T 24", TankClass.MEDIUM_TANK, 5, 5, 7, 10251);
    	createTankTypeAndUses(database, "Renault G1", TankClass.MEDIUM_TANK, 5, 5, 7, 2922);
    	createTankTypeAndUses(database, "Turan III prototipus", TankClass.MEDIUM_TANK, 5, 5, 7, 708);
    	createTankTypeAndUses(database, "Pz.Kpfw. V/IV Alpha", TankClass.MEDIUM_TANK, 5, 5, 6, 6);
    	createTankTypeAndUses(database, "Pz.Kpfw. V/IV", TankClass.MEDIUM_TANK, 5, 5, 6, 158);
    	createTankTypeAndUses(database, "Pz.Kpfw. T 25", TankClass.MEDIUM_TANK, 5, 5, 7, 11549);
    	createTankTypeAndUses(database, "Pz.Kpfw. IV hydrostat.", TankClass.MEDIUM_TANK, 5, 5, 6, 259);
    	createTankTypeAndUses(database, "Pz.Kpfw. IV Ausf. H", TankClass.MEDIUM_TANK, 5, 5, 7, 41156);
    	createTankTypeAndUses(database, "Pz.Kpfw. III/IV", TankClass.MEDIUM_TANK, 5, 5, 7, 9102);
    	createTankTypeAndUses(database, "Pz.Kpfw. III Ausf. K", TankClass.MEDIUM_TANK, 5, 5, 7, 769);
    	createTankTypeAndUses(database, "P.43", TankClass.MEDIUM_TANK, 5, 5, 7, 82292);
    	createTankTypeAndUses(database, "Type 3 Chi-Nu Kai", TankClass.MEDIUM_TANK, 5, 5, 7, 3925);
    	createTankTypeAndUses(database, "Type 3 Chi-Nu", TankClass.MEDIUM_TANK, 5, 5, 7, 4233);
    	createTankTypeAndUses(database, "Strv m/42", TankClass.MEDIUM_TANK, 5, 5, 7, 10674);
    	createTankTypeAndUses(database, "Sherman III", TankClass.MEDIUM_TANK, 5, 5, 7, 4539);
    	createTankTypeAndUses(database, "Matilda Black Prince", TankClass.MEDIUM_TANK, 5, 5, 6, 3790);
    	createTankTypeAndUses(database, "Crusader", TankClass.MEDIUM_TANK, 5, 5, 7, 11585);
    	createTankTypeAndUses(database, "Ram II", TankClass.MEDIUM_TANK, 5, 5, 7, 1274);
    	createTankTypeAndUses(database, "M4A2E4 Sherman", TankClass.MEDIUM_TANK, 5, 5, 6, 151);
    	createTankTypeAndUses(database, "M4 Sherman", TankClass.MEDIUM_TANK, 5, 5, 7, 28997);
    	createTankTypeAndUses(database, "M4 Improved", TankClass.MEDIUM_TANK, 5, 5, 7, 586);
    	createTankTypeAndUses(database, "T-34", TankClass.MEDIUM_TANK, 5, 5, 7, 29645);
    	createTankTypeAndUses(database, "Matilda IV", TankClass.MEDIUM_TANK, 5, 5, 7, 5780);
    	createTankTypeAndUses(database, "AMX 13 105 AM mle. 50", TankClass.SELF_PROPELLED_GUN, 5, 5, 7, 5825);
    	createTankTypeAndUses(database, "105 leFH18B2", TankClass.SELF_PROPELLED_GUN, 5, 5, 7, 8949);
    	createTankTypeAndUses(database, "Grille", TankClass.SELF_PROPELLED_GUN, 5, 5, 7, 26798);
    	createTankTypeAndUses(database, "Bishop", TankClass.SELF_PROPELLED_GUN, 5, 5, 7, 8780);
    	createTankTypeAndUses(database, "M41 HMC", TankClass.SELF_PROPELLED_GUN, 5, 5, 7, 14571);
    	createTankTypeAndUses(database, "SU-122A", TankClass.SELF_PROPELLED_GUN, 5, 5, 7, 5282);
    	createTankTypeAndUses(database, "60G FT", TankClass.TANK_DESTROYER, 5, 5, 7, 6600);
    	createTankTypeAndUses(database, "S35 CA", TankClass.TANK_DESTROYER, 5, 5, 7, 5617);
    	createTankTypeAndUses(database, "StuG IV", TankClass.TANK_DESTROYER, 5, 5, 6, 9771);
    	createTankTypeAndUses(database, "StuG III Ausf. G", TankClass.TANK_DESTROYER, 5, 5, 7, 26060);
    	createTankTypeAndUses(database, "Pz.Sfl. IVc", TankClass.TANK_DESTROYER, 5, 5, 7, 10584);
    	createTankTypeAndUses(database, "Ikv 103", TankClass.TANK_DESTROYER, 5, 5, 7, 17859);
    	createTankTypeAndUses(database, "AT 2", TankClass.TANK_DESTROYER, 5, 5, 7, 4427);
    	createTankTypeAndUses(database, "Archer", TankClass.TANK_DESTROYER, 5, 5, 7, 3995);
    	createTankTypeAndUses(database, "T67", TankClass.TANK_DESTROYER, 5, 5, 7, 57877);
    	createTankTypeAndUses(database, "M10 Wolverine", TankClass.TANK_DESTROYER, 5, 5, 7, 10604);
    	createTankTypeAndUses(database, "SU-85I", TankClass.TANK_DESTROYER, 5, 5, 6, 651);
    	createTankTypeAndUses(database, "SU-85", TankClass.TANK_DESTROYER, 5, 5, 7, 23907);
    	createTankTypeAndUses(database, "ARL 44", TankClass.HEAVY_TANK, 6, 6, 8, 12953);
    	createTankTypeAndUses(database, "VK 36.01 (H)", TankClass.HEAVY_TANK, 6, 6, 8, 20325);
    	createTankTypeAndUses(database, "Tiger 131", TankClass.HEAVY_TANK, 6, 6, 8, 5517);
    	createTankTypeAndUses(database, "O-I", TankClass.HEAVY_TANK, 6, 6, 8, 35510);
    	createTankTypeAndUses(database, "Heavy Tank No. VI", TankClass.HEAVY_TANK, 6, 6, 8, 9301);
    	createTankTypeAndUses(database, "TOG II*", TankClass.HEAVY_TANK, 6, 6, 7, 3659);
    	createTankTypeAndUses(database, "Churchill VII", TankClass.HEAVY_TANK, 6, 6, 8, 13007);
    	createTankTypeAndUses(database, "M6", TankClass.HEAVY_TANK, 6, 6, 8, 13182);
    	createTankTypeAndUses(database, "T-150", TankClass.HEAVY_TANK, 6, 6, 8, 21073);
    	createTankTypeAndUses(database, "KV-85", TankClass.HEAVY_TANK, 6, 6, 8, 38276);
    	createTankTypeAndUses(database, "KV-2", TankClass.HEAVY_TANK, 6, 6, 8, 47744);
    	createTankTypeAndUses(database, "Type 64", TankClass.LIGHT_TANK, 6, 6, 8, 37905);
    	createTankTypeAndUses(database, "59-16", TankClass.LIGHT_TANK, 6, 6, 8, 13097);
    	createTankTypeAndUses(database, "AMX 12 t", TankClass.LIGHT_TANK, 6, 6, 8, 41500);
    	createTankTypeAndUses(database, "VK 28.01", TankClass.LIGHT_TANK, 6, 6, 8, 16359);
    	createTankTypeAndUses(database, "T37", TankClass.LIGHT_TANK, 6, 6, 8, 15992);
    	createTankTypeAndUses(database, "T21", TankClass.LIGHT_TANK, 6, 6, 8, 7906);
    	createTankTypeAndUses(database, "MT-25", TankClass.LIGHT_TANK, 6, 6, 8, 17231);
    	createTankTypeAndUses(database, "Type 58", TankClass.MEDIUM_TANK, 6, 6, 8, 11177);
    	createTankTypeAndUses(database, "Skoda T 40", TankClass.MEDIUM_TANK, 6, 6, 8, 5422);
    	createTankTypeAndUses(database, "Skoda T 25", TankClass.MEDIUM_TANK, 6, 6, 8, 20374);
    	createTankTypeAndUses(database, "VK 30.02 (M)", TankClass.MEDIUM_TANK, 6, 6, 8, 10831);
    	createTankTypeAndUses(database, "VK 30.01 (P)", TankClass.MEDIUM_TANK, 6, 6, 8, 25754);
    	createTankTypeAndUses(database, "VK 30.01 (D)", TankClass.MEDIUM_TANK, 6, 6, 8, 9614);
    	createTankTypeAndUses(database, "Pz.Kpfw. IV Schmalturm", TankClass.MEDIUM_TANK, 6, 6, 8, 3634);
    	createTankTypeAndUses(database, "P.43 bis", TankClass.MEDIUM_TANK, 6, 6, 8, 67883);
    	createTankTypeAndUses(database, "Type 4 Chi-To", TankClass.MEDIUM_TANK, 6, 6, 8, 4172);
    	createTankTypeAndUses(database, "Pudel", TankClass.MEDIUM_TANK, 6, 6, 8, 9681);
    	createTankTypeAndUses(database, "Strv m/42-57 Alt A.2", TankClass.MEDIUM_TANK, 6, 6, 8, 8079);
    	createTankTypeAndUses(database, "Strv 74", TankClass.MEDIUM_TANK, 6, 6, 8, 16877);
    	createTankTypeAndUses(database, "Sherman Firefly", TankClass.MEDIUM_TANK, 6, 6, 8, 5553);
    	createTankTypeAndUses(database, "Cromwell B", TankClass.MEDIUM_TANK, 6, 6, 8, 16599);
    	createTankTypeAndUses(database, "Cromwell", TankClass.MEDIUM_TANK, 6, 6, 8, 38554);
    	createTankTypeAndUses(database, "AC 4 Experimental", TankClass.MEDIUM_TANK, 6, 6, 8, 1988);
    	createTankTypeAndUses(database, "M4A3E8 Thunderbolt VII", TankClass.MEDIUM_TANK, 6, 6, 8, 5003);
    	createTankTypeAndUses(database, "M4A3E8 Sherman", TankClass.MEDIUM_TANK, 6, 6, 8, 9682);
    	createTankTypeAndUses(database, "M4A3E8 Fury", TankClass.MEDIUM_TANK, 6, 6, 8, 1564);
    	createTankTypeAndUses(database, "M4A3E2 Sherman Jumbo", TankClass.MEDIUM_TANK, 6, 6, 8, 20443);
    	createTankTypeAndUses(database, "T-34-85M", TankClass.MEDIUM_TANK, 6, 6, 8, 70986);
    	createTankTypeAndUses(database, "T-34-85 Rudy", TankClass.MEDIUM_TANK, 6, 6, 8, 9933);
    	createTankTypeAndUses(database, "T-34-85", TankClass.MEDIUM_TANK, 6, 6, 8, 41860);
    	createTankTypeAndUses(database, "Loza M4-A2 Sherman", TankClass.MEDIUM_TANK, 6, 6, 8, 1842);
    	createTankTypeAndUses(database, "A-43", TankClass.MEDIUM_TANK, 6, 6, 8, 7105);
    	createTankTypeAndUses(database, "AMX 13 F3 AM", TankClass.SELF_PROPELLED_GUN, 6, 6, 8, 19741);
    	createTankTypeAndUses(database, "Hummel", TankClass.SELF_PROPELLED_GUN, 6, 6, 8, 32831);
    	createTankTypeAndUses(database, "FV304", TankClass.SELF_PROPELLED_GUN, 6, 6, 8, 15929);
    	createTankTypeAndUses(database, "M44", TankClass.SELF_PROPELLED_GUN, 6, 6, 8, 55589);
    	createTankTypeAndUses(database, "SU-8", TankClass.SELF_PROPELLED_GUN, 6, 6, 8, 11039);
    	createTankTypeAndUses(database, "WZ-131G FT", TankClass.TANK_DESTROYER, 6, 6, 8, 8872);
    	createTankTypeAndUses(database, "ARL V39", TankClass.TANK_DESTROYER, 6, 6, 8, 4201);
    	createTankTypeAndUses(database, "Nashorn", TankClass.TANK_DESTROYER, 6, 6, 8, 11694);
    	createTankTypeAndUses(database, "Jagdpanzer IV", TankClass.TANK_DESTROYER, 6, 6, 8, 21192);
    	createTankTypeAndUses(database, "Dicker Max", TankClass.TANK_DESTROYER, 6, 6, 8, 15324);
    	createTankTypeAndUses(database, "Ikv 65 Alt II", TankClass.TANK_DESTROYER, 6, 6, 8, 25461);
    	createTankTypeAndUses(database, "Churchill Gun Carrier", TankClass.TANK_DESTROYER, 6, 6, 8, 955);
    	createTankTypeAndUses(database, "AT 8", TankClass.TANK_DESTROYER, 6, 6, 8, 6406);
    	createTankTypeAndUses(database, "Achilles", TankClass.TANK_DESTROYER, 6, 6, 8, 6625);
    	createTankTypeAndUses(database, "M36 Jackson", TankClass.TANK_DESTROYER, 6, 6, 8, 10550);
    	createTankTypeAndUses(database, "M18 Hellcat", TankClass.TANK_DESTROYER, 6, 6, 8, 49690);
    	createTankTypeAndUses(database, "SU-100Y", TankClass.TANK_DESTROYER, 6, 6, 8, 11293);
    	createTankTypeAndUses(database, "SU-100", TankClass.TANK_DESTROYER, 6, 6, 8, 43383);
    	createTankTypeAndUses(database, "IS-2", TankClass.HEAVY_TANK, 7, 7, 9, 11614);
    	createTankTypeAndUses(database, "AMX M4 mle. 45", TankClass.HEAVY_TANK, 7, 7, 9, 14091);
    	createTankTypeAndUses(database, "VK 45.03", TankClass.HEAVY_TANK, 7, 7, 9, 4342);
    	createTankTypeAndUses(database, "VK 45.02 (P) Ausf. B7", TankClass.HEAVY_TANK, 7, 7, 9, 4);
    	createTankTypeAndUses(database, "Tiger I L/56", TankClass.HEAVY_TANK, 7, 7, 9, 1);
    	createTankTypeAndUses(database, "Tiger I", TankClass.HEAVY_TANK, 7, 7, 9, 44631);
    	createTankTypeAndUses(database, "Tiger (P)", TankClass.HEAVY_TANK, 7, 7, 9, 29112);
    	createTankTypeAndUses(database, "O-Ni", TankClass.HEAVY_TANK, 7, 7, 9, 34437);
    	createTankTypeAndUses(database, "FV201 (A45)", TankClass.HEAVY_TANK, 7, 7, 9, 893);
    	createTankTypeAndUses(database, "Black Prince", TankClass.HEAVY_TANK, 7, 7, 9, 14675);
    	createTankTypeAndUses(database, "T29", TankClass.HEAVY_TANK, 7, 7, 9, 45243);
    	createTankTypeAndUses(database, "KV-3", TankClass.HEAVY_TANK, 7, 7, 9, 13736);
    	createTankTypeAndUses(database, "KV-122", TankClass.HEAVY_TANK, 7, 7, 9, 2094);
    	createTankTypeAndUses(database, "IS-2", TankClass.HEAVY_TANK, 7, 7, 9, 4572);
    	createTankTypeAndUses(database, "IS", TankClass.HEAVY_TANK, 7, 7, 9, 57976);
    	createTankTypeAndUses(database, "WZ-131", TankClass.LIGHT_TANK, 7, 7, 9, 14943);
    	createTankTypeAndUses(database, "Type 62", TankClass.LIGHT_TANK, 7, 7, 9, 9906);
    	createTankTypeAndUses(database, "AMX 13 75", TankClass.LIGHT_TANK, 7, 7, 9, 41651);
    	createTankTypeAndUses(database, "AMX 13 57 GF", TankClass.LIGHT_TANK, 7, 7, 9, 6413);
    	createTankTypeAndUses(database, "AMX 13 57", TankClass.LIGHT_TANK, 7, 7, 9, 11424);
    	createTankTypeAndUses(database, "Spahpanzer SP I C", TankClass.LIGHT_TANK, 7, 7, 9, 16846);
    	createTankTypeAndUses(database, "T71 DA", TankClass.LIGHT_TANK, 7, 7, 9, 26795);
    	createTankTypeAndUses(database, "T71 CMCD", TankClass.LIGHT_TANK, 7, 7, 9, 9296);
    	createTankTypeAndUses(database, "LTG", TankClass.LIGHT_TANK, 7, 7, 9, 13096);
    	createTankTypeAndUses(database, "T-34-1", TankClass.MEDIUM_TANK, 7, 7, 9, 5164);
    	createTankTypeAndUses(database, "Konstrukta T-34/100", TankClass.MEDIUM_TANK, 7, 7, 9, 18928);
    	createTankTypeAndUses(database, "VK 30.02 (D)", TankClass.MEDIUM_TANK, 7, 7, 9, 10024);
    	createTankTypeAndUses(database, "Panther/M10", TankClass.MEDIUM_TANK, 7, 7, 8, 6287);
    	createTankTypeAndUses(database, "Panther", TankClass.MEDIUM_TANK, 7, 7, 9, 16189);
    	createTankTypeAndUses(database, "P.43 ter", TankClass.MEDIUM_TANK, 7, 7, 9, 38725);
    	createTankTypeAndUses(database, "Type 5 Chi-Ri", TankClass.MEDIUM_TANK, 7, 7, 9, 5819);
    	createTankTypeAndUses(database, "Leo", TankClass.MEDIUM_TANK, 7, 7, 9, 18846);
    	createTankTypeAndUses(database, "Comet", TankClass.MEDIUM_TANK, 7, 7, 9, 19526);
    	createTankTypeAndUses(database, "T23E3", TankClass.MEDIUM_TANK, 7, 7, 8, 170);
    	createTankTypeAndUses(database, "T20", TankClass.MEDIUM_TANK, 7, 7, 9, 15052);
    	createTankTypeAndUses(database, "T-44-85", TankClass.MEDIUM_TANK, 7, 7, 8, 1);
    	createTankTypeAndUses(database, "T-43", TankClass.MEDIUM_TANK, 7, 7, 9, 23367);
    	createTankTypeAndUses(database, "KV-13", TankClass.MEDIUM_TANK, 7, 7, 9, 5277);
    	createTankTypeAndUses(database, "A-44", TankClass.MEDIUM_TANK, 7, 7, 9, 7129);
    	createTankTypeAndUses(database, "Lorraine 155 mle. 50", TankClass.SELF_PROPELLED_GUN, 7, 7, 9, 21170);
    	createTankTypeAndUses(database, "G.W. Panther", TankClass.SELF_PROPELLED_GUN, 7, 7, 9, 58980);
    	createTankTypeAndUses(database, "Crusader 5.5-in. SP", TankClass.SELF_PROPELLED_GUN, 7, 7, 9, 11358);
    	createTankTypeAndUses(database, "M12", TankClass.SELF_PROPELLED_GUN, 7, 7, 9, 33724);
    	createTankTypeAndUses(database, "SU-14-1", TankClass.SELF_PROPELLED_GUN, 7, 7, 9, 11231);
    	createTankTypeAndUses(database, "S-51", TankClass.SELF_PROPELLED_GUN, 7, 7, 9, 14583);
    	createTankTypeAndUses(database, "T-34-2G FT", TankClass.TANK_DESTROYER, 7, 7, 9, 9066);
    	createTankTypeAndUses(database, "AMX AC mle. 46", TankClass.TANK_DESTROYER, 7, 7, 9, 4426);
    	createTankTypeAndUses(database, "Sturer Emil", TankClass.TANK_DESTROYER, 7, 7, 9, 14035);
    	createTankTypeAndUses(database, "Krupp-Steyr Waffentrager", TankClass.TANK_DESTROYER, 7, 7, 9, 6561);
    	createTankTypeAndUses(database, "Jagdpanther", TankClass.TANK_DESTROYER, 7, 7, 9, 28604);
    	createTankTypeAndUses(database, "E 25", TankClass.TANK_DESTROYER, 7, 7, 8, 27250);
    	createTankTypeAndUses(database, "Ikv 90 Typ B", TankClass.TANK_DESTROYER, 7, 7, 9, 29800);
    	createTankTypeAndUses(database, "Challenger", TankClass.TANK_DESTROYER, 7, 7, 9, 13202);
    	createTankTypeAndUses(database, "AT 7", TankClass.TANK_DESTROYER, 7, 7, 9, 5473);
    	createTankTypeAndUses(database, "AT 15A", TankClass.TANK_DESTROYER, 7, 7, 8, 2842);
    	createTankTypeAndUses(database, "T28 Concept", TankClass.TANK_DESTROYER, 7, 7, 9, 11424);
    	createTankTypeAndUses(database, "T25/2", TankClass.TANK_DESTROYER, 7, 7, 9, 28619);
    	createTankTypeAndUses(database, "T25 AT", TankClass.TANK_DESTROYER, 7, 7, 9, 6410);
    	createTankTypeAndUses(database, "M56 Scorpion", TankClass.TANK_DESTROYER, 7, 7, 9, 7986);
    	createTankTypeAndUses(database, "SU-152", TankClass.TANK_DESTROYER, 7, 7, 9, 16554);
    	createTankTypeAndUses(database, "SU-122-44", TankClass.TANK_DESTROYER, 7, 7, 9, 12068);
    	createTankTypeAndUses(database, "SU-100M1", TankClass.TANK_DESTROYER, 7, 7, 9, 47899);
    	createTankTypeAndUses(database, "ISU-122S", TankClass.TANK_DESTROYER, 7, 7, 9, 5190);
    	createTankTypeAndUses(database, "WZ-111 Alpine Tiger", TankClass.HEAVY_TANK, 8, 8, 10, 254);
    	createTankTypeAndUses(database, "WZ-111", TankClass.HEAVY_TANK, 8, 8, 9, 4795);
    	createTankTypeAndUses(database, "112", TankClass.HEAVY_TANK, 8, 8, 9, 7049);
    	createTankTypeAndUses(database, "110", TankClass.HEAVY_TANK, 8, 8, 10, 12626);
    	createTankTypeAndUses(database, "Somua SM", TankClass.HEAVY_TANK, 8, 8, 10, 13667);
    	createTankTypeAndUses(database, "FCM 50 t", TankClass.HEAVY_TANK, 8, 8, 9, 3805);
    	createTankTypeAndUses(database, "AMX M4 mle. 49 Liberte", TankClass.HEAVY_TANK, 8, 8, 10, 5369);
    	createTankTypeAndUses(database, "AMX M4 mle. 49", TankClass.HEAVY_TANK, 8, 8, 10, 7530);
    	createTankTypeAndUses(database, "AMX 65 t", TankClass.HEAVY_TANK, 8, 8, 10, 12159);
    	createTankTypeAndUses(database, "AMX 50 100", TankClass.HEAVY_TANK, 8, 8, 10, 15977);
    	createTankTypeAndUses(database, "VK 45.02 (P) Ausf. A", TankClass.HEAVY_TANK, 8, 8, 10, 8415);
    	createTankTypeAndUses(database, "VK 168.01 Mauerbrecher", TankClass.HEAVY_TANK, 8, 8, 10, 4696);
    	createTankTypeAndUses(database, "VK 100.01 (P) Ausf. B", TankClass.HEAVY_TANK, 8, 8, 10, 1);
    	createTankTypeAndUses(database, "VK 100.01 (P)", TankClass.HEAVY_TANK, 8, 8, 10, 25257);
    	createTankTypeAndUses(database, "Tiger II", TankClass.HEAVY_TANK, 8, 8, 10, 35167);
    	createTankTypeAndUses(database, "Lowe", TankClass.HEAVY_TANK, 8, 8, 10, 54369);
    	createTankTypeAndUses(database, "O-Ho", TankClass.HEAVY_TANK, 8, 8, 10, 34764);
    	createTankTypeAndUses(database, "Nameless", TankClass.HEAVY_TANK, 8, 8, 10, 3);
    	createTankTypeAndUses(database, "Emil I", TankClass.HEAVY_TANK, 8, 8, 10, 22950);
    	createTankTypeAndUses(database, "Caernarvon", TankClass.HEAVY_TANK, 8, 8, 10, 20421);
    	createTankTypeAndUses(database, "T34 B", TankClass.HEAVY_TANK, 8, 8, 10, 1596);
    	createTankTypeAndUses(database, "T34", TankClass.HEAVY_TANK, 8, 8, 10, 36476);
    	createTankTypeAndUses(database, "T32", TankClass.HEAVY_TANK, 8, 8, 10, 18493);
    	createTankTypeAndUses(database, "T26E5 Patriot", TankClass.HEAVY_TANK, 8, 8, 10, 9499);
    	createTankTypeAndUses(database, "T26E5", TankClass.HEAVY_TANK, 8, 8, 10, 8196);
    	createTankTypeAndUses(database, "M6A2E1", TankClass.HEAVY_TANK, 8, 8, 9, 2402);
    	createTankTypeAndUses(database, "Chrysler K GF", TankClass.HEAVY_TANK, 8, 8, 10, 4190);
    	createTankTypeAndUses(database, "Object 252U Defender", TankClass.HEAVY_TANK, 8, 8, 10, 12662);
    	createTankTypeAndUses(database, "Object 252U", TankClass.HEAVY_TANK, 8, 8, 10, 3563);
    	createTankTypeAndUses(database, "KV-5", TankClass.HEAVY_TANK, 8, 8, 9, 3054);
    	createTankTypeAndUses(database, "KV-4 Kreslavskiy", TankClass.HEAVY_TANK, 8, 8, 10, 46);
    	createTankTypeAndUses(database, "KV-4", TankClass.HEAVY_TANK, 8, 8, 10, 12781);
    	createTankTypeAndUses(database, "Kirovets-1", TankClass.HEAVY_TANK, 8, 8, 10, 1);
    	createTankTypeAndUses(database, "IS-M", TankClass.HEAVY_TANK, 8, 8, 10, 34207);
    	createTankTypeAndUses(database, "IS-6 B", TankClass.HEAVY_TANK, 8, 8, 9, 1038);
    	createTankTypeAndUses(database, "IS-6", TankClass.HEAVY_TANK, 8, 8, 9, 14774);
    	createTankTypeAndUses(database, "IS-5 (Object 730)", TankClass.HEAVY_TANK, 8, 8, 10, 337);
    	createTankTypeAndUses(database, "IS-3A", TankClass.HEAVY_TANK, 8, 8, 10, 1202);
    	createTankTypeAndUses(database, "IS-3", TankClass.HEAVY_TANK, 8, 8, 10, 72348);
    	createTankTypeAndUses(database, "WZ-132", TankClass.LIGHT_TANK, 8, 8, 10, 27327);
    	createTankTypeAndUses(database, "ELC EVEN 90", TankClass.LIGHT_TANK, 8, 8, 10, 19588);
    	createTankTypeAndUses(database, "Bat.-Chatillon 12 t", TankClass.LIGHT_TANK, 8, 8, 10, 64560);
    	createTankTypeAndUses(database, "leKpz M 41 90 mm GF", TankClass.LIGHT_TANK, 8, 8, 10, 18182);
    	createTankTypeAndUses(database, "leKpz M 41 90 mm", TankClass.LIGHT_TANK, 8, 8, 10, 16304);
    	createTankTypeAndUses(database, "HWK 12", TankClass.LIGHT_TANK, 8, 8, 10, 11912);
    	createTankTypeAndUses(database, "T92", TankClass.LIGHT_TANK, 8, 8, 10, 14838);
    	createTankTypeAndUses(database, "M41 Walker Bulldog", TankClass.LIGHT_TANK, 8, 8, 10, 19692);
    	createTankTypeAndUses(database, "LTTB", TankClass.LIGHT_TANK, 8, 8, 10, 20641);
    	createTankTypeAndUses(database, "Type 59", TankClass.MEDIUM_TANK, 8, 8, 9, 12525);
    	createTankTypeAndUses(database, "T-34-3", TankClass.MEDIUM_TANK, 8, 8, 9, 7955);
    	createTankTypeAndUses(database, "T-34-2", TankClass.MEDIUM_TANK, 8, 8, 10, 4458);
    	createTankTypeAndUses(database, "59-Patton", TankClass.MEDIUM_TANK, 8, 8, 10, 2508);
    	createTankTypeAndUses(database, "TVP VTU Koncept", TankClass.MEDIUM_TANK, 8, 8, 10, 16556);
    	createTankTypeAndUses(database, "M4A1 Revalorise", TankClass.MEDIUM_TANK, 8, 8, 10, 19419);
    	createTankTypeAndUses(database, "Lorraine 40 t", TankClass.MEDIUM_TANK, 8, 8, 10, 28815);
    	createTankTypeAndUses(database, "AMX Chasseur de chars", TankClass.MEDIUM_TANK, 8, 8, 10, 13417);
    	createTankTypeAndUses(database, "Schwarzpanzer 58", TankClass.MEDIUM_TANK, 8, 8, 10, 1121);
    	createTankTypeAndUses(database, "Panzer 58 Mutz", TankClass.MEDIUM_TANK, 8, 8, 10, 5495);
    	createTankTypeAndUses(database, "Panther mit 88 cm L/71", TankClass.MEDIUM_TANK, 8, 8, 10, 8807);
    	createTankTypeAndUses(database, "Panther II", TankClass.MEDIUM_TANK, 8, 8, 10, 12776);
    	createTankTypeAndUses(database, "Indien-Panzer", TankClass.MEDIUM_TANK, 8, 8, 10, 10618);
    	createTankTypeAndUses(database, "Progetto M35 mod 46", TankClass.MEDIUM_TANK, 8, 8, 10, 181065);
    	createTankTypeAndUses(database, "P.44 Pantera", TankClass.MEDIUM_TANK, 8, 8, 10, 30072);
    	createTankTypeAndUses(database, "STA-2", TankClass.MEDIUM_TANK, 8, 8, 10, 4104);
    	createTankTypeAndUses(database, "STA-1", TankClass.MEDIUM_TANK, 8, 8, 10, 5932);
    	createTankTypeAndUses(database, "Edelweiss", TankClass.MEDIUM_TANK, 8, 8, 10, 1);
    	createTankTypeAndUses(database, "Strv 81", TankClass.MEDIUM_TANK, 8, 8, 10, 2);
    	createTankTypeAndUses(database, "Primo Victoria", TankClass.MEDIUM_TANK, 8, 8, 10, 7136);
    	createTankTypeAndUses(database, "FV4202", TankClass.MEDIUM_TANK, 8, 8, 10, 24641);
    	createTankTypeAndUses(database, "Chieftain/T95", TankClass.MEDIUM_TANK, 8, 8, 10, 163);
    	createTankTypeAndUses(database, "Centurion Mk. I", TankClass.MEDIUM_TANK, 8, 8, 10, 19233);
    	createTankTypeAndUses(database, "Centurion Mk. 5/1 RAAC", TankClass.MEDIUM_TANK, 8, 8, 10, 3828);
    	createTankTypeAndUses(database, "T95E2", TankClass.MEDIUM_TANK, 8, 8, 10, 366);
    	createTankTypeAndUses(database, "T69", TankClass.MEDIUM_TANK, 8, 8, 10, 13054);
    	createTankTypeAndUses(database, "T26E4 SuperPershing", TankClass.MEDIUM_TANK, 8, 8, 9, 25820);
    	createTankTypeAndUses(database, "T25 Pilot Number 1", TankClass.MEDIUM_TANK, 8, 8, 10, 11471);
    	createTankTypeAndUses(database, "M46 Patton KR", TankClass.MEDIUM_TANK, 8, 8, 10, 4722);
    	createTankTypeAndUses(database, "M26 Pershing", TankClass.MEDIUM_TANK, 8, 8, 10, 14425);
    	createTankTypeAndUses(database, "T-54 first prototype", TankClass.MEDIUM_TANK, 8, 8, 10, 25918);
    	createTankTypeAndUses(database, "T-44-100", TankClass.MEDIUM_TANK, 8, 8, 10, 9354);
    	createTankTypeAndUses(database, "T-44", TankClass.MEDIUM_TANK, 8, 8, 10, 58660);
    	createTankTypeAndUses(database, "STG Guard", TankClass.MEDIUM_TANK, 8, 8, 10, 3221);
    	createTankTypeAndUses(database, "STG", TankClass.MEDIUM_TANK, 8, 8, 10, 956);
    	createTankTypeAndUses(database, "Object 416", TankClass.MEDIUM_TANK, 8, 8, 10, 8370);
    	createTankTypeAndUses(database, "Lorraine 155 mle. 51", TankClass.SELF_PROPELLED_GUN, 8, 8, 10, 24370);
    	createTankTypeAndUses(database, "G.W. Tiger (P)", TankClass.SELF_PROPELLED_GUN, 8, 8, 10, 39289);
    	createTankTypeAndUses(database, "FV207", TankClass.SELF_PROPELLED_GUN, 8, 8, 10, 12841);
    	createTankTypeAndUses(database, "M40/M43", TankClass.SELF_PROPELLED_GUN, 8, 8, 10, 39473);
    	createTankTypeAndUses(database, "SU-14-2", TankClass.SELF_PROPELLED_GUN, 8, 8, 10, 15786);
    	createTankTypeAndUses(database, "WZ-120-1G FT", TankClass.TANK_DESTROYER, 8, 8, 10, 23999);
    	createTankTypeAndUses(database, "WZ-111-1G FT", TankClass.TANK_DESTROYER, 8, 8, 10, 8731);
    	createTankTypeAndUses(database, "AMX Canon dassaut 105", TankClass.TANK_DESTROYER, 8, 8, 10, 4468);
    	createTankTypeAndUses(database, "AMX AC mle. 48", TankClass.TANK_DESTROYER, 8, 8, 10, 7042);
    	createTankTypeAndUses(database, "Rhm.-Borsig Waffentrager", TankClass.TANK_DESTROYER, 8, 8, 10, 43559);
    	createTankTypeAndUses(database, "Rheinmetall Skorpion G", TankClass.TANK_DESTROYER, 8, 8, 10, 125035);
    	createTankTypeAndUses(database, "Rheinmetall Skorpion", TankClass.TANK_DESTROYER, 8, 8, 10, 1);
    	createTankTypeAndUses(database, "Kanonenjagdpanzer", TankClass.TANK_DESTROYER, 8, 8, 10, 857);
    	createTankTypeAndUses(database, "Jagdpanther II", TankClass.TANK_DESTROYER, 8, 8, 10, 19107);
    	createTankTypeAndUses(database, "Ferdinand", TankClass.TANK_DESTROYER, 8, 8, 10, 18580);
    	createTankTypeAndUses(database, "88 cm Pak 43 Jagdtiger", TankClass.TANK_DESTROYER, 8, 8, 9, 9276);
    	createTankTypeAndUses(database, "UDES 03", TankClass.TANK_DESTROYER, 8, 8, 10, 43279);
    	createTankTypeAndUses(database, "Strv S1", TankClass.TANK_DESTROYER, 8, 8, 10, 47781);
    	createTankTypeAndUses(database, "Charioteer", TankClass.TANK_DESTROYER, 8, 8, 10, 17479);
    	createTankTypeAndUses(database, "AT 15", TankClass.TANK_DESTROYER, 8, 8, 10, 6401);
    	createTankTypeAndUses(database, "T28 Prototype", TankClass.TANK_DESTROYER, 8, 8, 10, 32718);
    	createTankTypeAndUses(database, "T28", TankClass.TANK_DESTROYER, 8, 8, 10, 11734);
    	createTankTypeAndUses(database, "T-103", TankClass.TANK_DESTROYER, 8, 8, 10, 3);
    	createTankTypeAndUses(database, "SU-101", TankClass.TANK_DESTROYER, 8, 8, 10, 61210);
    	createTankTypeAndUses(database, "ISU-152", TankClass.TANK_DESTROYER, 8, 8, 10, 26868);
    	createTankTypeAndUses(database, "WZ-111 model 1-4", TankClass.HEAVY_TANK, 9, 9, 11, 26123);
    	createTankTypeAndUses(database, "AMX M4 mle. 51", TankClass.HEAVY_TANK, 9, 9, 11, 8785);
    	createTankTypeAndUses(database, "AMX 50 120", TankClass.HEAVY_TANK, 9, 9, 11, 11110);
    	createTankTypeAndUses(database, "VK 45.02 (P) Ausf. B", TankClass.HEAVY_TANK, 9, 9, 11, 13642);
    	createTankTypeAndUses(database, "Mauschen", TankClass.HEAVY_TANK, 9, 9, 11, 12992);
    	createTankTypeAndUses(database, "E 75", TankClass.HEAVY_TANK, 9, 9, 11, 36979);
    	createTankTypeAndUses(database, "Type 4 Heavy", TankClass.HEAVY_TANK, 9, 9, 11, 42658);
    	createTankTypeAndUses(database, "Emil II", TankClass.HEAVY_TANK, 9, 9, 11, 17113);
    	createTankTypeAndUses(database, "Conqueror", TankClass.HEAVY_TANK, 9, 9, 11, 33985);
    	createTankTypeAndUses(database, "M103", TankClass.HEAVY_TANK, 9, 9, 11, 21008);
    	createTankTypeAndUses(database, "T-10", TankClass.HEAVY_TANK, 9, 9, 11, 80765);
    	createTankTypeAndUses(database, "ST-I", TankClass.HEAVY_TANK, 9, 9, 11, 12823);
    	createTankTypeAndUses(database, "Object 705", TankClass.HEAVY_TANK, 9, 9, 11, 23075);
    	createTankTypeAndUses(database, "Object 257", TankClass.HEAVY_TANK, 9, 9, 11, 55633);
    	createTankTypeAndUses(database, "WZ-132A", TankClass.LIGHT_TANK, 9, 9, 11, 11966);
    	createTankTypeAndUses(database, "AMX 13 90", TankClass.LIGHT_TANK, 9, 9, 11, 41986);
    	createTankTypeAndUses(database, "Spahpanzer Ru 251", TankClass.LIGHT_TANK, 9, 9, 11, 23095);
    	createTankTypeAndUses(database, "T49", TankClass.LIGHT_TANK, 9, 9, 11, 37939);
    	createTankTypeAndUses(database, "T-54 ltwt.", TankClass.LIGHT_TANK, 9, 9, 11, 29456);
    	createTankTypeAndUses(database, "WZ-120", TankClass.MEDIUM_TANK, 9, 9, 11, 8398);
    	createTankTypeAndUses(database, "Skoda T 50", TankClass.MEDIUM_TANK, 9, 9, 11, 26376);
    	createTankTypeAndUses(database, "Bat.-Chatillon 25 t AP", TankClass.MEDIUM_TANK, 9, 9, 11, 31095);
    	createTankTypeAndUses(database, "AMX 30 1er prototype", TankClass.MEDIUM_TANK, 9, 9, 11, 13738);
    	createTankTypeAndUses(database, "T 55A", TankClass.MEDIUM_TANK, 9, 9, 11, 8011);
    	createTankTypeAndUses(database, "Leopard Prototyp A", TankClass.MEDIUM_TANK, 9, 9, 11, 18363);
    	createTankTypeAndUses(database, "E 50", TankClass.MEDIUM_TANK, 9, 9, 11, 16388);
    	createTankTypeAndUses(database, "Prototipo Standard B", TankClass.MEDIUM_TANK, 9, 9, 11, 20394);
    	createTankTypeAndUses(database, "Type 61", TankClass.MEDIUM_TANK, 9, 9, 11, 6068);
    	createTankTypeAndUses(database, "Centurion Mk. 7/1", TankClass.MEDIUM_TANK, 9, 9, 11, 20448);
    	createTankTypeAndUses(database, "T54E1", TankClass.MEDIUM_TANK, 9, 9, 11, 16559);
    	createTankTypeAndUses(database, "M46 Patton", TankClass.MEDIUM_TANK, 9, 9, 11, 16750);
    	createTankTypeAndUses(database, "T-54", TankClass.MEDIUM_TANK, 9, 9, 11, 44863);
    	createTankTypeAndUses(database, "Object 430 Version II", TankClass.MEDIUM_TANK, 9, 9, 11, 14228);
    	createTankTypeAndUses(database, "Object 430", TankClass.MEDIUM_TANK, 9, 9, 11, 42581);
    	createTankTypeAndUses(database, "Bat.-Chatillon 155 55", TankClass.SELF_PROPELLED_GUN, 9, 9, 11, 23593);
    	createTankTypeAndUses(database, "G.W. Tiger", TankClass.SELF_PROPELLED_GUN, 9, 9, 11, 23300);
    	createTankTypeAndUses(database, "FV3805", TankClass.SELF_PROPELLED_GUN, 9, 9, 11, 11829);
    	createTankTypeAndUses(database, "M53/M55", TankClass.SELF_PROPELLED_GUN, 9, 9, 11, 46353);
    	createTankTypeAndUses(database, "212A", TankClass.SELF_PROPELLED_GUN, 9, 9, 11, 13993);
    	createTankTypeAndUses(database, "WZ-111G FT", TankClass.TANK_DESTROYER, 9, 9, 11, 5619);
    	createTankTypeAndUses(database, "AMX 50 Foch", TankClass.TANK_DESTROYER, 9, 9, 11, 6096);
    	createTankTypeAndUses(database, "Waffentrager auf Pz. IV", TankClass.TANK_DESTROYER, 9, 9, 11, 32265);
    	createTankTypeAndUses(database, "Jagdtiger", TankClass.TANK_DESTROYER, 9, 9, 11, 30118);
    	createTankTypeAndUses(database, "Strv 103-0", TankClass.TANK_DESTROYER, 9, 9, 11, 34646);
    	createTankTypeAndUses(database, "Tortoise", TankClass.TANK_DESTROYER, 9, 9, 11, 9987);
    	createTankTypeAndUses(database, "FV4004 Conway", TankClass.TANK_DESTROYER, 9, 9, 11, 16524);
    	createTankTypeAndUses(database, "T95", TankClass.TANK_DESTROYER, 9, 9, 11, 19811);
    	createTankTypeAndUses(database, "T30", TankClass.TANK_DESTROYER, 9, 9, 11, 40587);
    	createTankTypeAndUses(database, "Object 704", TankClass.TANK_DESTROYER, 9, 9, 11, 17751);
    	createTankTypeAndUses(database, "Object 263", TankClass.TANK_DESTROYER, 9, 9, 11, 54353);
    	createTankTypeAndUses(database, "WZ-111 model 5A", TankClass.HEAVY_TANK, 10, 10, 12, 11914);
    	createTankTypeAndUses(database, "113", TankClass.HEAVY_TANK, 10, 10, 12, 5669);
    	createTankTypeAndUses(database, "AMX M4 mle. 54", TankClass.HEAVY_TANK, 10, 10, 12, 2997);
    	createTankTypeAndUses(database, "AMX 50 B", TankClass.HEAVY_TANK, 10, 10, 12, 9669);
    	createTankTypeAndUses(database, "VK 72.01 (K)", TankClass.HEAVY_TANK, 10, 10, 12, 1351);
    	createTankTypeAndUses(database, "Pz.Kpfw. VII", TankClass.HEAVY_TANK, 10, 10, 12, 5811);
    	createTankTypeAndUses(database, "Maus", TankClass.HEAVY_TANK, 10, 10, 12, 10173);
    	createTankTypeAndUses(database, "E 100", TankClass.HEAVY_TANK, 10, 10, 12, 26812);
    	createTankTypeAndUses(database, "Type 5 Heavy", TankClass.HEAVY_TANK, 10, 10, 12, 19450);
    	createTankTypeAndUses(database, "Kranvagn", TankClass.HEAVY_TANK, 10, 10, 12, 5041);
    	createTankTypeAndUses(database, "T95/Chieftain", TankClass.HEAVY_TANK, 10, 10, 12, 1);
    	createTankTypeAndUses(database, "Super Conqueror", TankClass.HEAVY_TANK, 10, 10, 12, 19101);
    	createTankTypeAndUses(database, "FV215b", TankClass.HEAVY_TANK, 10, 10, 12, 2697);
    	createTankTypeAndUses(database, "Chieftain Mk. 6", TankClass.HEAVY_TANK, 10, 10, 12, 4);
    	createTankTypeAndUses(database, "T57 Heavy Tank", TankClass.HEAVY_TANK, 10, 10, 12, 16548);
    	createTankTypeAndUses(database, "T110E5", TankClass.HEAVY_TANK, 10, 10, 12, 13657);
    	createTankTypeAndUses(database, "Object 777 Version II", TankClass.HEAVY_TANK, 10, 10, 12, 1);
    	createTankTypeAndUses(database, "Object 705A", TankClass.HEAVY_TANK, 10, 10, 12, 11188);
    	createTankTypeAndUses(database, "Object 260", TankClass.HEAVY_TANK, 10, 10, 12, 3166);
    	createTankTypeAndUses(database, "Object 257 (P)", TankClass.HEAVY_TANK, 10, 10, 12, 1);
    	createTankTypeAndUses(database, "IS-7", TankClass.HEAVY_TANK, 10, 10, 12, 52083);
    	createTankTypeAndUses(database, "IS-4", TankClass.HEAVY_TANK, 10, 10, 12, 6921);
    	createTankTypeAndUses(database, "WZ-132-1", TankClass.LIGHT_TANK, 10, 10, 12, 4151);
    	createTankTypeAndUses(database, "AMX 13 105", TankClass.LIGHT_TANK, 10, 10, 12, 16451);
    	createTankTypeAndUses(database, "Rheinmetall Panzerwagen", TankClass.LIGHT_TANK, 10, 10, 12, 6104);
    	createTankTypeAndUses(database, "XM551 Sheridan", TankClass.LIGHT_TANK, 10, 10, 12, 9713);
    	createTankTypeAndUses(database, "T-100 LT", TankClass.LIGHT_TANK, 10, 10, 12, 29715);
    	createTankTypeAndUses(database, "121B", TankClass.MEDIUM_TANK, 10, 10, 12, 427);
    	createTankTypeAndUses(database, "121", TankClass.MEDIUM_TANK, 10, 10, 12, 4981);
    	createTankTypeAndUses(database, "TVP T 50/51", TankClass.MEDIUM_TANK, 10, 10, 12, 22157);
    	createTankTypeAndUses(database, "Bat.-Chatillon 25 t", TankClass.MEDIUM_TANK, 10, 10, 12, 43615);
    	createTankTypeAndUses(database, "AMX 30 B", TankClass.MEDIUM_TANK, 10, 10, 12, 4302);
    	createTankTypeAndUses(database, "Leopard 1", TankClass.MEDIUM_TANK, 10, 10, 12, 12024);
    	createTankTypeAndUses(database, "E 50 Ausf. M", TankClass.MEDIUM_TANK, 10, 10, 12, 12285);
    	createTankTypeAndUses(database, "Progetto M40 mod. 65", TankClass.MEDIUM_TANK, 10, 10, 12, 8911);
    	createTankTypeAndUses(database, "STB-1", TankClass.MEDIUM_TANK, 10, 10, 12, 3994);
    	createTankTypeAndUses(database, "Centurion Action X", TankClass.MEDIUM_TANK, 10, 10, 12, 11780);
    	createTankTypeAndUses(database, "T95E6", TankClass.MEDIUM_TANK, 10, 10, 12, 425);
    	createTankTypeAndUses(database, "M60", TankClass.MEDIUM_TANK, 10, 10, 12, 1459);
    	createTankTypeAndUses(database, "M48A5 Patton", TankClass.MEDIUM_TANK, 10, 10, 12, 13946);
    	createTankTypeAndUses(database, "M48A2/T54E2/T123E6", TankClass.MEDIUM_TANK, 10, 10, 12, 1);
    	createTankTypeAndUses(database, "T-62A", TankClass.MEDIUM_TANK, 10, 10, 12, 11715);
    	createTankTypeAndUses(database, "T-22 medium", TankClass.MEDIUM_TANK, 10, 10, 12, 120);
    	createTankTypeAndUses(database, "Object 907", TankClass.MEDIUM_TANK, 10, 10, 12, 5602);
    	createTankTypeAndUses(database, "Object 430U", TankClass.MEDIUM_TANK, 10, 10, 12, 22430);
    	createTankTypeAndUses(database, "Object 430B", TankClass.MEDIUM_TANK, 10, 10, 12, 4);
    	createTankTypeAndUses(database, "Object 140", TankClass.MEDIUM_TANK, 10, 10, 12, 29747);
    	createTankTypeAndUses(database, "Bat.-Chatillon 155 58", TankClass.SELF_PROPELLED_GUN, 10, 10, 12, 20403);
    	createTankTypeAndUses(database, "G.W. E 100", TankClass.SELF_PROPELLED_GUN, 10, 10, 12, 20741);
    	createTankTypeAndUses(database, "Conqueror Gun Carriage", TankClass.SELF_PROPELLED_GUN, 10, 10, 12, 11682);
    	createTankTypeAndUses(database, "T92 HMC", TankClass.SELF_PROPELLED_GUN, 10, 10, 12, 20053);
    	createTankTypeAndUses(database, "Object 261", TankClass.SELF_PROPELLED_GUN, 10, 10, 12, 15031);
    	createTankTypeAndUses(database, "WZ-113G FT", TankClass.TANK_DESTROYER, 10, 10, 12, 1028);
    	createTankTypeAndUses(database, "AMX 50 Foch B", TankClass.TANK_DESTROYER, 10, 10, 12, 3442);
    	createTankTypeAndUses(database, "AMX 50 Foch (155)", TankClass.TANK_DESTROYER, 10, 10, 12, 3039);
    	createTankTypeAndUses(database, "Jagdpanzer E 100", TankClass.TANK_DESTROYER, 10, 10, 12, 31725);
    	createTankTypeAndUses(database, "Grille 15", TankClass.TANK_DESTROYER, 10, 10, 12, 30817);
    	createTankTypeAndUses(database, "Strv 103B", TankClass.TANK_DESTROYER, 10, 10, 12, 17510);
    	createTankTypeAndUses(database, "FV4005 Stage II", TankClass.TANK_DESTROYER, 10, 10, 12, 20186);
    	createTankTypeAndUses(database, "FV217 Badger", TankClass.TANK_DESTROYER, 10, 10, 12, 7728);
    	createTankTypeAndUses(database, "FV215b (183)", TankClass.TANK_DESTROYER, 10, 10, 12, 17904);
    	createTankTypeAndUses(database, "T110E4", TankClass.TANK_DESTROYER, 10, 10, 12, 23034);
    	createTankTypeAndUses(database, "T110E3", TankClass.TANK_DESTROYER, 10, 10, 12, 10520);
    	createTankTypeAndUses(database, "Object 268 Version 4", TankClass.TANK_DESTROYER, 10, 10, 12, 53487);
    	createTankTypeAndUses(database, "Object 268", TankClass.TANK_DESTROYER, 10, 10, 12, 11406);
    	createTankTypeAndUses(database, "Object 263B", TankClass.TANK_DESTROYER, 10, 10, 12, 8);
    }
    
    private static void createTankTypeAndUses(Database database, String name, TankClass tankClass, Integer tier, Integer minBattleTier, Integer maxBattleTier, Integer appearances)
    {
    	TankType tt = new TankType(name, tankClass, tier, minBattleTier, maxBattleTier);
    	TankUse tu = new TankUse(tt, appearances);
    	
    	database.execute(s -> { s.persist(tt); s.persist(tu); });
    }
}
