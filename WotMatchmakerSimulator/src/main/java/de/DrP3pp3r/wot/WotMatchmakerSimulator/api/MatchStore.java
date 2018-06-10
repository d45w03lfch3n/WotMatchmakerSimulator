package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.database.Database;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Match;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Session;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;

public class MatchStore
{
	public MatchStore(Database database)
	{
		this.database = database;
		session = new Session();

		this.database.execute(s ->
		{
			s.persist(session);
		});
	}

	// public <T extends Match> T createMatch()
	// {
	// T match = new T();
	// match.setSession(session);
	// }

	public void storeMatch(Match match)
	{
		match.setSession(session);

		this.database.execute(s ->
		{
			s.persist(match);
		});
	}

	public void storeUnmatchedTank(TankType tankType)
	{
		UnmatchedTank unmatchedTank = new UnmatchedTank(session, tankType);
		this.database.execute(s ->
		{
			s.persist(unmatchedTank);
		});
	}

	private Session session;
	private Database database;

}
