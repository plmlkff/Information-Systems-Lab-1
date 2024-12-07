package ru.itmo.is_lab1.config;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import ru.itmo.is_lab1.domain.entity.*;

import java.io.IOException;
import java.util.Properties;

@ApplicationScoped
public class HibernateConfig {

    @RequestScoped
    static class SessionConfig{
        @Inject
        private SessionFactory sessionFactory;

        private Session session;

        @Produces
        @RequestScoped
        public Session createSession(){
            session = sessionFactory == null ? null : sessionFactory.openSession();
            return session;
        }

        @PreDestroy
        public void closeSession() {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Produces
    @ApplicationScoped
    private SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            properties.load(HibernateConfig.class.getClassLoader().getResourceAsStream("/hibernate.cfg.properties"));
            SessionFactory sessionFactory = new Configuration().configure().setProperty(AvailableSettings.USER, properties.getProperty(AvailableSettings.USER))
                    .setProperty(AvailableSettings.PASS, properties.getProperty(AvailableSettings.PASS))
                    .setProperty(AvailableSettings.SHOW_SQL, properties.getProperty(AvailableSettings.SHOW_SQL))
                    .setProperty(AvailableSettings.HBM2DDL_AUTO, properties.getProperty(AvailableSettings.HBM2DDL_AUTO))
                    .setProperty(AvailableSettings.POOL_SIZE, properties.getProperty(AvailableSettings.POOL_SIZE))
                    .addAnnotatedClass(Album.class)
                    .addAnnotatedClass(Coordinates.class)
                    .addAnnotatedClass(MusicBand.class)
                    .addAnnotatedClass(Studio.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(EntityChangeHistory.class)
                    .addAnnotatedClass(FileUploadHistory.class).buildSessionFactory();
            return sessionFactory;
        } catch (IOException ex) {
            System.err.println("Something went wrong");
        }
        return null;
    }
}
