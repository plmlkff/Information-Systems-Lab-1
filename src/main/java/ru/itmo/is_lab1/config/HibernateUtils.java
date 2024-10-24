package ru.itmo.is_lab1.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import ru.itmo.is_lab1.domain.entity.Album;
import ru.itmo.is_lab1.domain.entity.Coordinates;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.entity.Studio;

import java.io.IOException;
import java.util.Properties;

@ApplicationScoped
public class HibernateUtils {
    @Produces
    public Session createSession(){
        var sessionFactory = buildSessionFactory();
        return sessionFactory == null ? null : sessionFactory.openSession();
    }

    private SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            properties.load(HibernateUtils.class.getClassLoader().getResourceAsStream("/hibernate.cfg.properties"));
            System.out.println(properties.getProperty("password"));
            SessionFactory sessionFactory = new Configuration().configure().setProperty(AvailableSettings.USER, properties.getProperty(AvailableSettings.USER)).
                    setProperty(AvailableSettings.PASS, properties.getProperty(AvailableSettings.PASS))
                    .setProperty(AvailableSettings.SHOW_SQL, properties.getProperty(AvailableSettings.SHOW_SQL))
                    .setProperty(AvailableSettings.HBM2DDL_AUTO, properties.getProperty(AvailableSettings.HBM2DDL_AUTO))
                    .addAnnotatedClass(Album.class).addAnnotatedClass(Coordinates.class)
                    .addAnnotatedClass(MusicBand.class).addAnnotatedClass(Studio.class).buildSessionFactory();
            return sessionFactory;
        } catch (IOException ex) {
            System.err.println("Something went wrong");
        }
        return null;
    }
}
