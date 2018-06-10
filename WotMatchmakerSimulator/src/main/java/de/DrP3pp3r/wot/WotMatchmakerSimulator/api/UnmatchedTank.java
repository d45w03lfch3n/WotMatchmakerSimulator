package de.DrP3pp3r.wot.WotMatchmakerSimulator.api;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Session;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;

@Entity
public class UnmatchedTank
{
	public UnmatchedTank()
	{

	}

	public UnmatchedTank(Session session, TankType tankType)
	{
		this.session = session;
		this.tankType = tankType;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Session getSession()
	{
		return session;
	}

	public void setSession(Session session)
	{
		this.session = session;
	}

	public TankType getTankType()
	{
		return tankType;
	}

	public void setTankType(TankType tankType)
	{
		this.tankType = tankType;
	}

	@Id
	private Integer id;
	@OneToOne
	private Session session;
	@OneToOne
	private TankType tankType;
}
