package pl.sebcel.genealogy.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	private static Session session;

    static {
        try {
        	sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session openSession() throws HibernateException {
        session = sessionFactory.openSession(); 
    	return session;
    }

    public static Session getSession() throws HibernateException {
    	if (session==null) openSession();
        return session;
    }
    
    public static void closeSession() {
    	session.close();
    	session = null;
    }
    
    public static void beginTransaction() {
    	Session session = getSession();
    	session.beginTransaction();
    }
    
    public static void commit() {
    	Session session = getSession();
    	session.getTransaction().commit();
    }
}