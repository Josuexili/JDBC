package com.esports.persistence;

import com.esports.model.Atleta;
import com.esports.model.Esport;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Esport.class)
                    .addAnnotatedClass(Atleta.class)
                    .buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError("Error inicialitzant Hibernate: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        if (SESSION_FACTORY != null) {
            SESSION_FACTORY.close();
        }
    }
}
