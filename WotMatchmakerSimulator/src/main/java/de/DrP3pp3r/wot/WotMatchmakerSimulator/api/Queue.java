package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankTypeSelector;

public class Queue
{
	
	public Queue(TankTypeSelector tankTypeSelector)
	{
		this.tankTypeSelector = tankTypeSelector;
	}

	private TankTypeSelector tankTypeSelector;

}
