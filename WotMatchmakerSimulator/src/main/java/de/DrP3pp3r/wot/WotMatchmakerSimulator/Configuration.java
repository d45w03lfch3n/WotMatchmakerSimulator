package de.DrP3pp3r.wot.WotMatchmakerSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

public Configuration(String configFilePath)
{
	File configFile = new File("config.properties");
	 
	try
	{
	    FileReader reader = new FileReader(configFile);
	    Properties props = new Properties();
	    props.load(reader);
	 
	    matchmakerPath = props.getProperty("matchmakerPath");
	 
	    System.out.format("matchmakerPath=%s\n", matchmakerPath);
	    reader.close();
	}
	catch (FileNotFoundException ex)
	{
		System.out.format("%s", ex.getMessage());
	}
	catch (IOException ex)
	{
		System.out.format("%s", ex.getMessage());
	}
}

public String getMatchmakerPath()
{
	return matchmakerPath;
}

public void setMatchmakerPath(String matchmakerPath)
{
	this.matchmakerPath = matchmakerPath;
}

private String matchmakerPath;

}
