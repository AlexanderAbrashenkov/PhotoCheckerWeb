package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ResponsibilityDAO extends GenericDAO<Responsibility> {

    @Override
    boolean create(Responsibility responsibility) throws PersistException;

    @Override
    Responsibility find(int id) throws PersistException;

    @Override
    List<Responsibility> findAll() throws PersistException;

    @Override
    boolean update(Responsibility responsibility) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (User user)
     * @return List<Responsibility>
     * @throws PersistException
     */
    @Override
    List<Responsibility> findAllByParameters(Object... params) throws PersistException;
}