package de.DrP3pp3r.wot.mm_sim;

public class TankType
{
	
public TankType(String name, TankClass tankClass) 
{
	this.name = name;
	this.tankClass = tankClass;
}

public String getName()
{
	return name;
}

public TankClass getTankClass()
{
	return tankClass;
}

private final String name;
private final TankClass tankClass;

}
