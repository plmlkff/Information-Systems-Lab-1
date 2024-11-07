package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.service.MusicBandService;

import java.util.List;

@ApplicationScoped
public class MusicBandServiceImpl implements MusicBandService {
    @Inject
    private MusicBandDAO musicBandDAO;
    @Inject
    private UserDAO userDAO;

    @Override
    public List<MusicBand> getAll() throws CanNotGetAllEntitiesException {
        return musicBandDAO.findAll();
    }

    @Override
    public MusicBand save(MusicBand musicBand, String ownerLogin) throws CanNotSaveEntityException {
        try {
            User owner = userDAO.findById(ownerLogin);
            musicBand.setOwner(owner);
            return musicBandDAO.save(musicBand);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotSaveEntityException("User not found!");
        }
    }

    @Override
    public MusicBand getById(Integer id) throws CanNotGetByIdEntityException {
        return musicBandDAO.findById(id);
    }
}
