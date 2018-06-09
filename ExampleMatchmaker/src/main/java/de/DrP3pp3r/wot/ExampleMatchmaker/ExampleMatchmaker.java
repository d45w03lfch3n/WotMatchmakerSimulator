package de.DrP3pp3r.wot.ExampleMatchmaker;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.API;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Match;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.MatchmakerUtils;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.TeamFullException;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.ThreadedMatchmaker;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;

public class ExampleMatchmaker extends ThreadedMatchmaker
{
	public ExampleMatchmaker()
	{
	
	}
	
	@Override
	public void runImpl()
	{
		matchedTanks = 0;
		finishedMatches = 0;
		
		MatchmakerUtils utils = new MatchmakerUtils();
		
		initializeMatches();
		
		API api = getApi();
		api.getQueue().start(500);
			
		while(shouldRun())
		{
			TankType tank = api.getQueue().getNextTank();
			if(tank != null)
			{
				Integer battleTier = utils.rollBattleTier(tank.getMinBattleTier(), tank.getMaxBattleTier());
				assert battleTier >= 1 && battleTier <= 12 : "Bad battle tier!";
				Match match = matchesByBattleTier[battleTier - 1];				
				if(!match.getGreenTeam().isFull())
				{
					try
					{
						match.getGreenTeam().addNewTankType(tank);
						++matchedTanks;
					}
					catch (TeamFullException e)
					{
						assert false : "Internal error: green team is full.";
					}
				}
				else
				{
					try
					{
						match.getRedTeam().addNewTankType(tank);
						++matchedTanks;
					}
					catch (TeamFullException e)
					{
						assert false : "Internal error: red team is full.";
					}
					
					if(match.getRedTeam().isFull()) 
					{
						//System.out.format("Finished a tier '%d' match.\n", battleTier);
						api.getMatchStore().storeMatch(match);
						++finishedMatches;
						matchesByBattleTier[battleTier - 1] = new Match(battleTier);
					}
				}
			}
			else
			{
				try
				{
					Thread.sleep(100);
				} 
				catch (InterruptedException e)
				{
				}
			}
		}
		
		api.getQueue().stop();
		
		System.out.format("Matchmaker created '%d' matches and matched '%d' tanks.\n", getFinishedMatches(), getMatchedTanks());
	}

	public Integer getMatchedTanks() {
		return matchedTanks;
	}

	public Integer getFinishedMatches() {
		return finishedMatches;
	}

	private void initializeMatches()
	{
		// TODO magic consts
		matchesByBattleTier = new Match[12];
		for(Integer i = 1; i <= 12; ++i)
		{
			matchesByBattleTier[i - 1] = new Match(i);
		}
	}

	
	private Match[] matchesByBattleTier;
	private Integer matchedTanks;
	private Integer finishedMatches;
}
