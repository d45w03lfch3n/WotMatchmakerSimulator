package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

public class Match {
	
public Match()
{
	team = new Team[2];
}

public Team getTeam1()
{
	return team[0];
}

public Team getTeam2()
{
	return team[1];
}

private Team[] team;

}
