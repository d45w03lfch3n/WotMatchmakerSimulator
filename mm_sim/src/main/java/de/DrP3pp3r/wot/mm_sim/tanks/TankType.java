package de.DrP3pp3r.wot.mm_sim.tanks;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TankType
{
	
public TankType()
{
}
	
public TankType(String name, TankClass tankClass, Integer tier, Integer minBattleTier, Integer maxBattleTier) 
{
	this.name = name;
	this.tankClass = tankClass;
	this.tier = tier;
	this.minBattleTier = minBattleTier;
	this.maxBattleTier = maxBattleTier;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName()
{
	return name;
}

public void setName(String name) {
	this.name = name;
}

public TankClass getTankClass()
{
	return tankClass;
}

public void setTankClass(TankClass tankClass) {
	this.tankClass = tankClass;
}

public Integer getTier()
{
	return tier;
}

public void setTier(Integer tier) {
	this.tier = tier;
}

public Integer getMinBattleTier()
{
	return minBattleTier;
}

public void setMinBattleTier(Integer minBattleTier) {
	this.minBattleTier = minBattleTier;
}

public Integer getMaxBattleTier()
{
	return maxBattleTier;
}

public void setMaxBattleTier(Integer maxBattleTier) {
	this.maxBattleTier = maxBattleTier;
}

@Id
private Integer id;
private String name;
private TankClass tankClass;
private Integer tier;
private Integer minBattleTier;
private Integer maxBattleTier;

}
