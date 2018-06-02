package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//TODO check if teams are ok for battle tiers
@Entity
public class Match {
	
public Match()
{
}
	
public Match(Integer tier)
{
	greenTeam = new Team();
	redTeam = new Team();
	this.tier = tier;
}

public Team getGreenTeam()
{
	return greenTeam;
}

public void setGreenTeam(Team greenTeam) {
	this.greenTeam = greenTeam;
}

public Team getRedTeam()
{
	return redTeam;
}

public void setRedTeam(Team redTeam) {
	this.redTeam = redTeam;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Integer getTier() {
	return tier;
}

public void setTier(Integer tier) {
	this.tier = tier;
}

public Session getSession() {
	return session;
}

public void setSession(Session session) {
	this.session = session;
}

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id;
private Integer tier;
@OneToOne(cascade = CascadeType.PERSIST)
private Team greenTeam;
@OneToOne(cascade = CascadeType.PERSIST)
private Team redTeam;
@OneToOne
private Session session;

}
