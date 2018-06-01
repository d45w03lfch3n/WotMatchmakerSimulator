package de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TankUse {
	
public TankUse()
{
	
}
	
public TankUse(TankType tankType, int useCount)
{
	this.tankType = tankType;
	this.useCount = useCount;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public TankType getTankType()
{
	return tankType;
}

public void setTankType(TankType tankType) {
	this.tankType = tankType;
}

public int getUseCount()
{
	return useCount;
}

public void setUseCount(Integer useCount) {
	this.useCount = useCount;
}

@Override
public String toString()
{
	return String.format("Tank use for tank '%s' is '%d'.", tankType.getName(), useCount);
}

@Id
private Integer id;
@OneToOne
private TankType tankType;
private Integer useCount;

}
