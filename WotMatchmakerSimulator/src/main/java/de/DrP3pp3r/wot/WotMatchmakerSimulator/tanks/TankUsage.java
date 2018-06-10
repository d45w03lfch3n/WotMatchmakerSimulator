package de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks;

import java.util.ArrayList;
import java.util.List;

public class TankUsage
{
	public TankUsage()
	{
		tankUses = new ArrayList<TankUse>();
	}

	public void addTankUse(TankUse tankUse)
	{
		tankUses.add(tankUse);
	}

	public void setTankUses(List<TankUse> tankUses)
	{
		this.tankUses = tankUses;
	}

	public TankTypeSelector buildTankTypeSelector()
	{
		TankTypeSelector selector = new TankTypeSelector();

		final int totalUses = tankUses.stream().mapToInt(u -> u.getUseCount()).sum();
		System.out.format("Total number of uses is '%d'.\n", totalUses);

		double offset = 0.0;
		for(TankUse tankUse : tankUses)
		{
			final double ratio = (double) tankUse.getUseCount() / totalUses;
			offset += ratio;
			if(offset > 1.0)
			{
				offset = 1.0;
			}
			selector.addSelectionInfo(new TankTypeSelectionInfo(tankUse.getTankType(), offset));
			System.out.format("Ratio of tank '%s' is '%.4f' and offset is '%.4f'.\n", tankUse.getTankType().getName(), ratio,
					offset);
		}

		return selector;
	}

	@Override
	public String toString()
	{
		String result = "Tank uses:\n";
		for(TankUse u : tankUses)
		{
			result += u.toString() + '\n';
		}
		return result;
	}

	private List<TankUse> tankUses;

}
