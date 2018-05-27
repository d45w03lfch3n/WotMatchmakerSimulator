package de.DrP3pp3r.wot.mm_sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TankTypeSelector 
{

public TankTypeSelector()
{
	selectionInfos = new ArrayList<TankTypeSelectionInfo>();
}

public void addSelectionInfo(TankTypeSelectionInfo selectionInfo)
{
	selectionInfos.add(selectionInfo);
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

private List<TankTypeSelectionInfo> selectionInfos;
	
}
