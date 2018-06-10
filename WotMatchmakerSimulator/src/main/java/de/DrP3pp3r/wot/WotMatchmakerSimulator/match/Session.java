package de.DrP3pp3r.wot.WotMatchmakerSimulator.match;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Session
{
	public Session()
	{
		// sessionId = UUID.randomUUID();
	}

	// public UUID getSessionId() {
	// return sessionId;
	// }
	//
	// public void setSessionId(UUID sessionId) {
	// this.sessionId = sessionId;
	// }

	// @Id
	// private UUID sessionId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

}
