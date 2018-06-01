package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankTypeSelector;

public class Queue implements Runnable
{	
	public Queue(TankTypeSelector tankTypeSelector)
	{
		this.tankTypeSelector = tankTypeSelector;
		tankTypes = new ConcurrentLinkedQueue<TankType>();
		doRun = new AtomicBoolean(false);
	}
	
	public void start(Integer tanksPerSecond)
	{
		if(doRun.compareAndSet(false, true))
		{
			assert workerThread == null : "There already is a worker thread!";
			tankTypes.clear();
			this.tanksPerSecond = tanksPerSecond;
			workerThread = new Thread(this);
			workerThread.start();
		}
		else
		{
			System.out.println("Queue already running!");
		}
	}
	
	public Boolean stop()
	{
		Boolean stopped = false;
		try
		{
			if(doRun.get())
			{
				doRun.set(false);
				workerThread.join();
				stopped = true;
				workerThread = null;
			}
			else
			{
				System.out.println("Queue not running!");
			}
		}
		catch(InterruptedException ex)
		{
			// nothing to do...
		}
		
		return stopped;
	}
	
	@Override
	public void run() {
		try
		{
			final long ns_per_s = 1_000_000_000;
			final long ns_per_ms = 1_000_000;
			final long ms_per_s = 1_000;
			while(doRun.get())
			{
				long start = System.nanoTime();
				for(Integer i = 0; i < tanksPerSecond; ++i) {				
					tankTypes.add(tankTypeSelector.getRandomTankType());
				}
				long end = System.nanoTime();
				long diff = end - start;
				System.out.format("Diff: %d\n", diff);
				if(diff > ns_per_s)
				{
					// too slow
					System.out.format("Queue is too slow. It took '%d' ms for '%d' tanks!\n", diff/ns_per_ms, tanksPerSecond);
				}
				else
				{
					long ms_to_sleep = ms_per_s - diff/ns_per_ms;
					//System.out.format("Queue has spare time ('%d' ns).\n", diff);
					Thread.sleep(ms_to_sleep);
				}
			}
			
			System.out.format("Tanks in queue: '%d'", tankTypes.size());
		}
		catch(InterruptedException ex)
		{
			System.out.format("Queue was interrupted!\n");
			doRun.set(false);
		}
	}	

	private TankTypeSelector tankTypeSelector;
	private ConcurrentLinkedQueue<TankType> tankTypes;
	private Integer tanksPerSecond;
	private Thread workerThread;
	private AtomicBoolean doRun;
}
