package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.StudioDAO;
import ru.itmo.is_lab1.domain.entity.Studio;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.service.StudioService;

@ApplicationScoped
public class StudioServiceImpl implements StudioService {
    @Inject
    private StudioDAO studioDAO;

    @Override
    public Studio getById(Integer id) throws CanNotGetByIdEntityException {
        var studio = studioDAO.findById(id);
        if (studio == null) throw new CanNotGetByIdEntityException("Id does not exist!");
        return studio;
    }
}
