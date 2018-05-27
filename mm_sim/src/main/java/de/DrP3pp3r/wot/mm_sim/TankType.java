package de.DrP3pp3r.wot.mm_sim;

public class TankType
{
	
public TankType(String name, TankClass tankClass, int tier, int minBattleTier, int maxBattleTier) 
{
	this.name = name;
	this.tankClass = tankClass;
	this.tier = tier;
	this.minBattleTier = minBattleTier;
	this.maxBattleTier = maxBattleTier;
}

public String getName()
{
	return name;
}

public TankClass getTankClass()
{
	return tankClass;
}

public int getTier()
{
	return tier;
}

public int getMinBattleTier()
{
	return minBattleTier;
}

public int getMaxBattleTier()
{
	return maxBattleTier;
}

private final String name;
private final TankClass tankClass;
private final int tier;
private final int minBattleTier;
private final int maxBattleTier;

}
