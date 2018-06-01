package de.DrP3pp3r.wot.WotMatchmakerSimulator.database;

import org.hibernate.Session;

public interface IFunction<T>
{
	T execute(Session session);
}
