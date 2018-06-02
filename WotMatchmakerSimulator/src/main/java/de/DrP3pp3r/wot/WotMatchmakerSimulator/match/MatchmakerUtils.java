package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import java.util.Random;

public class MatchmakerUtils {
	
public MatchmakerUtils()
{
	rng = new Random();
}

public Integer rollBattleTier(Integer minTier, Integer maxTier)
{
	return rng.nextInt(maxTier - minTier + 1) + minTier;
}


private Random rng;

}
