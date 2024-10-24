package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is_lab1.domain.dao.StudioDAO;
import ru.itmo.is_lab1.domain.entity.Studio;

@ApplicationScoped
public class StudioDAOImpl extends BasicDAOImpl<Studio, Integer> implements StudioDAO {
}
