package com.dam.tfg.MotoMammiApplicationIDS.services.implement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;

@Component
public class ProcessImpl implements ProcessService{
    @Override
    public void readFileInfo(String source) {
        System.out.println(source);
        // call to database
        SessionFactory sessionFactory;

        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.getTransaction().commit();
        session.close();
    }
}
