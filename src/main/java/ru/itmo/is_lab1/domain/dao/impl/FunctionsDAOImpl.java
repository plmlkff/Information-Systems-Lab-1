package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.itmo.is_lab1.domain.dao.FunctionsDAO;
import ru.itmo.is_lab1.domain.entity.MusicGenre;
import ru.itmo.is_lab1.exceptions.domain.CanNotExecuteFunctionException;

@ApplicationScoped
public class FunctionsDAOImpl implements FunctionsDAO {
    @Inject
    private Session session;

    @Override
    public int calculateTotalAlbumsCount() throws CanNotExecuteFunctionException {
        try {
            Query<Integer> query = session.createQuery("select calculate_total_albums_count()", Integer.class);
            return query.getSingleResult();
        } catch (Exception e){
            throw new CanNotExecuteFunctionException("Can not calculate total albums");
        }
    }

    @Override
    public int countAlbumsGreaterThan(int value) throws CanNotExecuteFunctionException {
        try {
            Query<Integer> query = session.createQuery("select count_albums_greater_than(?1)", Integer.class);
            query.setParameter(1, value);
            return query.getSingleResult();
        } catch (Exception e){
            throw new CanNotExecuteFunctionException("Can not count albums greater than");
        }
    }

    @Override
    public void increaseParticipants(int id) throws CanNotExecuteFunctionException {
        try {
            Query<Integer> query = session.createQuery("select increase_participants(?1)", Integer.class);
            query.setParameter(1, id);
            query.getSingleResult();
        } catch (Exception e){
            throw new CanNotExecuteFunctionException("Can not increase participants");
        }
    }

    @Override
    public void deleteMusicBand(int id) throws CanNotExecuteFunctionException {
        try {
            Query<Void> query = session.createQuery("select delete_musicband(?1)", Void.class);
            query.setParameter(1, id);
            query.getSingleResult();
        } catch (Exception e){
            throw new CanNotExecuteFunctionException("Can not delete music band");
        }
    }

    @Override
    public int countMusicBandsByGenre(MusicGenre genre) throws CanNotExecuteFunctionException {
        try {
            Query<Integer> query = session.createQuery("select count_music_bands_by_genre(?1)", Integer.class);
            query.setParameter(1, genre.toString());
            return query.getSingleResult();
        } catch (Exception e){
            throw new CanNotExecuteFunctionException("Can not count music bands by genre");
        }
    }
}
