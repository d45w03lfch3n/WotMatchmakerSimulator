package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Match;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Session;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;

public class MatchStore {
public MatchStore()
{
	session = new Session();
}

public <T extends Match> T createMatch()
{
	T match = new T();
	match.setSession(session);
}

public void storeMatch(Match match)
{
	
}

public void storeUnmatchedTank(TankType tankType)
{
	
}

private Session session;

}
