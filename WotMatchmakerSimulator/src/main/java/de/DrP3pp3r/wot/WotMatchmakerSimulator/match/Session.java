package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session
{
public Session()
{
	sessionId = UUID.randomUUID();
}

public UUID getSessionId() {
	return sessionId;
}

public void setSessionId(UUID sessionId) {
	this.sessionId = sessionId;
}

@Id
private UUID sessionId;

}
