package de.DrP3pp3r.wot.WotMatchmakerSimulator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
		
		MatchmakerOptions matchmakerOptions = parseCommandline(args);

		Boolean optionCreateTankTypes = false;
		
		//Configuration configuration = new Configuration("config.properties");

		Database database = new Database();

		if(optionCreateTankTypes)
		{
			DatabaseInitializer.createTankTypesAndTankUses(database);
			return;
		}
		
		IMatchmaker matchmaker = MatchmakerLoader.loadMatchmaker2(matchmakerOptions.matchmakerJarPath);
		if(matchmaker == null)
		{
			System.out.format("No matchmaker! Qutting.\n");
		}
		else
		{
			API api = ApiBuilder.buildApi(database);
			
			final String configurationPath = matchmakerOptions.matchmakerConfigPath;
			final Long runDurationInMs = matchmakerOptions.matchmakerDurationInS * 1000;
			runMatchmaker(matchmaker, api, runDurationInMs, configurationPath);
		}
		
		System.out.format("MatchmakerSimulation is done!\n");
}

	private static MatchmakerOptions parseCommandline(String[] args)
	{
		Options options = new Options();
		
		Option matchmakerJarOption = new Option("j", "matchmaker_jar", true, "Path to the jar containing the matchmaker implementation.");
		matchmakerJarOption.setRequired(true);
		
		Option matchmakerConfigOption = new Option("c", "matchmaker_config", true, "Path to the configuration file of the matchmaker.");
		matchmakerConfigOption.setRequired(false);
		
		Option matchmakerDurationOption = new Option("d", "matchmaker_duration", true, "Duration of the matchmaker run in s.");
		matchmakerDurationOption.setRequired(false);
		matchmakerDurationOption.setType(Number.class);
		
		options.addOption(matchmakerJarOption);
		options.addOption(matchmakerConfigOption);
		options.addOption(matchmakerDurationOption);
		
		MatchmakerOptions matchmakerOptions = new MatchmakerOptions();
		
		CommandLineParser parser = new DefaultParser();
		try
		{
			CommandLine commandLine = parser.parse(options, args);
			matchmakerOptions.matchmakerJarPath = commandLine.getOptionValue("j");
			matchmakerOptions.matchmakerConfigPath = commandLine.getOptionValue("c", "");
			if(commandLine.hasOption("d"))
			{
				matchmakerOptions.matchmakerDurationInS = ((Number)commandLine.getParsedOptionValue("d")).longValue();
			}
			else
			{
				matchmakerOptions.matchmakerDurationInS = 10l;
			}
		}
		catch(ParseException e)
		{
			System.out.format("Bad commandline: %s\n", e.getMessage());
			matchmakerOptions = null;
		}
		
		return matchmakerOptions;
	}

	private static void runMatchmaker(IMatchmaker matchmaker, API api, Long runDurationInMs, String configurationPath)
	{
		matchmaker.setApi(api);
		matchmaker.setConfigurationPath(configurationPath);
		
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
