package com.dam.tfg.MotoMammiApplicationIDS.Utils;
import com.dam.tfg.MotoMammiApplicationIDS.model.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.InterfaceDTO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;


    /**
     * Crea la factoria de sesiones
     */
    public static synchronized void buildSessionFactory() {
        try {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(ProviderDTO.class);
        configuration.addAnnotatedClass(CustomerDTO.class);
        configuration.addAnnotatedClass(InterfaceDTO.class);
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
     * Inicia una transacción
     */
    public static synchronized void beginTransaction() {
        if (transaction == null || !transaction.isActive()) {
            transaction = session.beginTransaction();
        }
    }

    /**
     * Confirma una transacción
     */
    public static synchronized void commitTransaction() {
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
        }
    }

    /**
     * Revierte una transacción
     */
    public static synchronized void rollbackTransaction() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
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