package de.DrP3pp3r.wot.WotMatchmakerSimulator.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import de.DrP3pp3r.wot.WotMatchmakerSimulator.api.UnmatchedTank;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Match;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Team;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankType;
import de.DrP3pp3r.wot.WotMatchmakerSimulator.tanks.TankUse;

public class Database
{
	public Database() throws HibernateException
	{
		configureSessionFactory();
	}

	public <T> T execute(IFunction<T> function)
	{
		Session session = null;
		Transaction transaction = null;

		T result = null;

		try
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			result = function.execute(session);

			session.flush();
			transaction.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();

			result = null;

			if(transaction != null)
			{
				transaction.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}

		return result;
	}

	public void execute(IProcedure procedure)
	{
		Session session = null;
		Transaction transaction = null;

		try
		{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();

			procedure.execute(session);

			session.flush();
			transaction.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();

			if(transaction != null)
			{
				transaction.rollback();
			}
		}
		finally
		{
			if(session != null)
			{
				session.close();
			}
		}
	}

	private static void configureSessionFactory() throws HibernateException
	{
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
				.build();
		MetadataSources sources = new MetadataSources(standardRegistry)
				.addAnnotatedClass(TankType.class)
				.addAnnotatedClass(TankUse.class)
				.addAnnotatedClass(de.DrP3pp3r.wot.WotMatchmakerSimulator.match.Session.class)
				.addAnnotatedClass(Match.class)
				.addAnnotatedClass(UnmatchedTank.class)
				.addAnnotatedClass(Team.class);
		MetadataBuilder metadataBuilder = sources.getMetadataBuilder();
		Metadata metaData = metadataBuilder.applyImplicitSchemaName("MatchmakerSimulator").build();
		sessionFactory = metaData.getSessionFactoryBuilder().build();
	}

	private static SessionFactory sessionFactory = null;
}
