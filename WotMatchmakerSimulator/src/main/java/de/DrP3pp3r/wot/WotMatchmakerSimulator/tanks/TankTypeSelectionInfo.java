package de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks;

public class TankTypeSelectionInfo implements Comparable<TankTypeSelectionInfo>
{
	public TankTypeSelectionInfo(TankType tankType, double topValue)
	{
		assert topValue >= 0.0 : "Top value is < 0.0";
		assert topValue <= 1.0 : "Top value is > 1.0";

		this.tankType = tankType;
		this.topValue = topValue;
	}

	public TankType getTankType()
	{
		return tankType;
	}

	public double getTopValue()
	{
		return topValue;
	}

	@Override
	public int compareTo(TankTypeSelectionInfo rhs)
	{
		return Double.compare(topValue, rhs.topValue);
	}

	@Override
	public String toString()
	{
		return String.format("Tank selection info: '%s' is '%d'.", tankType.getName(), topValue);
	}

	private final TankType tankType;
	private final double topValue;

}
