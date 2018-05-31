package de.DrP3pp3r.wot.mm_sim.tanks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TankTypeSelector 
{

public TankTypeSelector()
{
	selectionInfos = new ArrayList<TankTypeSelectionInfo>();
	rng = new Random();
}

public void addSelectionInfo(TankTypeSelectionInfo selectionInfo)
{
	selectionInfos.add(selectionInfo);
}

public TankType getRandomTankType()
{
	final double selectorValue = rng.nextDouble();
	//System.out.format("Selector value form RNG is '%.4f'.\n", selectorValue);
	TankType selectedTankType = getTankType(selectorValue);
	assert selectedTankType != null : "Selected tank type is 'null'!";
	//System.out.format("Selected tank type is '%s'.\n", selectedTankType.getName());
	return selectedTankType;
}

public TankType getTankType(double value)
{
	assert value >= 0.0 : "Value is < 0.0";
	assert value <= 1.0 : "Value is > 1.0";
	
	TankTypeSelectionInfo i = 
			selectionInfos
				.stream()
            	.filter((TankTypeSelectionInfo si) -> { return si.getTopValue() > value; })
            	.findFirst()
            	.get();
	
	return i.getTankType();
}

// not needed
public void prepare()
{
	Collections.sort(selectionInfos);
}

@Override
public String toString()
{
	String result = "Tank selection infos:\n";
	for(TankTypeSelectionInfo si : selectionInfos)
	{
		result += si.toString() + '\n';
	}
	return result;
}

private List<TankTypeSelectionInfo> selectionInfos;
private Random rng;
	
}
