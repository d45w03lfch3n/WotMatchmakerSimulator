package de.DrP3pp3r.wot.mm_sim.tanks;

public class TankUse {
	
public TankUse(TankType tankType, int useCount)
{
	this.tankType = tankType;
	this.useCount = useCount;
}

public TankType getTankType()
{
	return tankType;
}

public int getUseCount()
{
	return useCount;
}
	
	
private final TankType tankType;
private final int useCount;

}
