package dao.utils;

import dao.entities.hibernate.Answers;
import dao.entities.hibernate.Questions;
import dao.entities.hibernate.Users;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateDriver {
    private static HibernateDriver instance;
    private static SessionFactory factory;
    private static final Object EMPTY_OBJECT = new Object();

    private HibernateDriver() {
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(Users.class);
            config.addAnnotatedClass(Questions.class);
            config.addAnnotatedClass(Answers.class);
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            factory = config.buildSessionFactory(builder.build());
        } catch (Throwable e) {
            System.out.println("Connection failed");
        }
    }

    public static HibernateDriver getInstance() {
        synchronized (EMPTY_OBJECT) {
            if (instance == null) {
                instance = new HibernateDriver();
            }
        }
        return instance;
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
