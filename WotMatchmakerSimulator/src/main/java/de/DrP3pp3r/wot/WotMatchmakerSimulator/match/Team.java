package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;

@Entity
public class Team
{
	
public Team()
{
	tankTypes = new ArrayList<TankType>();
}

public Integer getId()
{
	return id;
}

public void setId(Integer id)
{
	this.id = id;
}

public List<TankType> getTankTypes()
{
	return tankTypes;
}

public void setTankTypes(List<TankType> tankTypes)
{
	this.tankTypes = tankTypes;
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

//public List<TankType> getTankTypes()
//{
//	List<TankType> unmodifiableList = Collections.unmodifiableList(tankTypes);
//	return unmodifiableList;
//}

public Integer getSize()
{
	return tankTypes.size();
}

public Boolean isFull()
{
	return tankTypes.size() == MAX_TEAM_SIZE;
}

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id;

@OneToMany
private List<TankType> tankTypes;

private static final int MAX_TEAM_SIZE = 15;

}
