package org.epita.game2.infrastructure;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .buildSessionFactory();
        }
        return sessionFactory;
    }
}
