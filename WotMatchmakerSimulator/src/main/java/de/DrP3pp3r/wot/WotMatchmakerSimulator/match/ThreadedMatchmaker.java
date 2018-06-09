package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import java.util.concurrent.atomic.AtomicBoolean;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.API;

public abstract class ThreadedMatchmaker implements IMatchmaker, Runnable
{
	public ThreadedMatchmaker()
	{
		doRun = new AtomicBoolean(false);
	}
	
	@Override
	public void setApi(API api) {
		this.api = api;		
	}

	protected API getApi() {
		return api;
	}

	@Override
	public void start()
	{
		assert api != null : "No API set!";
		
		if(doRun.compareAndSet(false, true))
		{
			assert workerThread == null : "There already is a worker thread!";
			
			workerThread = new Thread(this);
			workerThread.start();
		}
		else
		{
			assert false : "Matchmaker already running!";
		}
	}

	@Override
	public void stop()
	{		
		try
		{
			if(doRun.get())
			{
				doRun.set(false);
				workerThread.join();
				workerThread = null;
			}
			else
			{
				assert false : "Matchmaker not running!";
			}
		}
		catch(InterruptedException ex)
		{
			// nothing to do...
		}
	}
	
	@Override
	public void run() 
	{
		runImpl();
	}
	
	public abstract void runImpl();
	
	protected Boolean shouldRun()
	{
		return doRun.get();
	}

	
	private API api;
	
	private Thread workerThread;
	private AtomicBoolean doRun;
}
