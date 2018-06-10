package de.DrP3pp3r.wot.WotMatchmakerSimulator;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.API;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.ApiBuilder;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.MatchmakerLoader;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.database.Database;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.IMatchmaker;

public class MatchmakerSimulation
{
	public static void main(String[] args)
	{
		System.out.println("MatchmakerSimulation was started!");

		Boolean optionCreateTankTypes = false;
		
		Configuration configuration = new Configuration("config.properties");

		Database database = new Database();

		if(optionCreateTankTypes)
		{
			DatabaseInitializer.createTankTypesAndTankUses(database);
			return;
		}
		
		// TODO get from command line
		IMatchmaker matchmaker = MatchmakerLoader.loadMatchmaker2(configuration.getMatchmakerPath());
		if(matchmaker == null)
		{
			System.out.format("No matchmaker! Qutting.\n");
		}
		else
		{
			API api = ApiBuilder.buildApi(database);
			
			// TODO get matchmaker config file path from command line
					
			// TODO get from command line
			Long runDurationInMs = 10000l;
			runMatchmaker(matchmaker, api, runDurationInMs);
		}
		
		System.out.format("MatchmakerSimulation is done!\n");
}

	private static void runMatchmaker(IMatchmaker matchmaker, API api, Long runDurationInMs)
	{
		matchmaker.setApi(api);
		
		matchmaker.start();
		
		try
		{
			Thread.sleep(runDurationInMs);
		}
		catch (InterruptedException e)
		{
		}
		
		matchmaker.stop();
	}
}
