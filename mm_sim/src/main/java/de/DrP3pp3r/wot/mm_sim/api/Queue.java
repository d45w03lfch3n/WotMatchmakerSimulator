package de.DrP3pp3r.wot.mm_sim.api;

import de.DrP3pp3r.wot.mm_sim.tanks.TankTypeSelector;

public class Queue
{
	
	public Queue(TankTypeSelector tankTypeSelector)
	{
		this.tankTypeSelector = tankTypeSelector;
	}

	private TankTypeSelector tankTypeSelector;

}
