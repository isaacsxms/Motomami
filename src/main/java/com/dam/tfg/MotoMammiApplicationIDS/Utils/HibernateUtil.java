package com.dam.tfg.MotoMammiApplicationIDS.Utils;
import com.dam.tfg.MotoMammiApplicationIDS.model.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.CustomerDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Session session;

    /**
     * Crea la factoria de sesiones
     */
    public static synchronized void buildSessionFactory() {
        try {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(ProviderDTO.class);
        configuration.addAnnotatedClass(CustomerDTO.class);
        ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(sr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to build session factory: " + e.getMessage(), e);
        }
    }

    /**
     * Abre una nueva sesión
     */
    public static synchronized void openSession() {
        session = sessionFactory.openSession();
    }

    /**
     * Devuelve la sesión actual
     * @return
     */
    public static synchronized Session getCurrentSession() {

        if ((session == null) || (!session.isOpen()))
            openSession();

        return session;
    }

    /**
     * Cierra Hibernate
     */
    public static void closeSessionFactory() {

        if (session != null)
            session.close();

        if (sessionFactory != null)
            sessionFactory.close();
    }
}