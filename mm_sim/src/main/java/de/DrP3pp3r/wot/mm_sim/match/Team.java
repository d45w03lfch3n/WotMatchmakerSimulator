package de.DrP3pp3r.wot.mm_sim.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.DrP3pp3r.wot.mm_sim.tanks.TankType;

public class Team
{
	
public Team()
{
	tankTypes = new ArrayList<TankType>();
}

public void addNewTankType(TankType newTankType) throws TeamFullException
{
	if(tankTypes.size() < MAX_TEAM_SIZE)
	{
		tankTypes.add(newTankType);
	}
	else
	{
		throw new TeamFullException("Team is already full!");
	}
}

public List<TankType> getTankTypes()
{
	List<TankType> unmodifiableList = Collections.unmodifiableList(tankTypes);
	return unmodifiableList;
}

private List<TankType> tankTypes;

private static final int MAX_TEAM_SIZE = 15;

}
