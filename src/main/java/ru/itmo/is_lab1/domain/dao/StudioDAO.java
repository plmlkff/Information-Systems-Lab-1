package ru.itmo.is_lab1.domain.dao;

import ru.itmo.is_lab1.domain.entity.Studio;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetStudioByAddressException;

public interface StudioDAO extends AbstractDAO<Studio, Integer> {
    Studio findByAddress(String address) throws CanNotGetStudioByAddressException;
}
