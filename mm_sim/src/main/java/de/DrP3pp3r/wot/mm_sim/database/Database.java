package de.DrP3pp3r.wot.mm_sim.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import de.DrP3pp3r.wot.mm_sim.tanks.TankType;
import de.DrP3pp3r.wot.mm_sim.tanks.TankUse;

public class Database
{
	public Database() throws HibernateException
	{
		configureSessionFactory();
	}
    
    public <T> T execute(IQuery<T> query)
    {
    	Session session = null;
        Transaction transaction = null;
        
        T result = null;
         
        try
        {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            result = query.execute(session);
             
            session.flush();
            transaction.commit();

            return result;
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
    
    private static void configureSessionFactory() throws HibernateException
    {        
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        MetadataSources sources = new MetadataSources(standardRegistry)
        			.addAnnotatedClass(TankType.class)
        			.addAnnotatedClass(TankUse.class);
        MetadataBuilder metadataBuilder = sources.getMetadataBuilder();
        Metadata metaData =  metadataBuilder.applyImplicitSchemaName( "MatchmakerSimulator" ).build();
        sessionFactory = metaData.getSessionFactoryBuilder().build();
    }
    
    private static SessionFactory sessionFactory = null;
}
