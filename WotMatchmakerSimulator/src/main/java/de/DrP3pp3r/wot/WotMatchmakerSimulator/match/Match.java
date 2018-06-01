package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Match {
	
public Match(Session session)
{
	team = new Team[2];
	this.session = session;
}

public Team getTeam1()
{
	return team[0];
}

public Team getTeam2()
{
	return team[1];
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Team[] getTeam() {
	return team;
}

public void setTeam(Team[] team) {
	this.team = team;
}

public Session getSession() {
	return session;
}

public void setSession(Session session) {
	this.session = session;
}

@Id
private Integer id;
private Team[] team;
@OneToOne
private Session session;

}
