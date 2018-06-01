package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankTypeSelector;

public class API {

	public API(TankTypeSelector tankTypeSelector)
	{
		queue = new Queue(tankTypeSelector);
		matchStore = new MatchStore();
	}
	
	public Queue getQueue()
	{
		return queue;
	}
	
	public MatchStore getMatchStore()
	{
		return matchStore;
	}
	
	private Queue queue;
	private MatchStore matchStore;
}
