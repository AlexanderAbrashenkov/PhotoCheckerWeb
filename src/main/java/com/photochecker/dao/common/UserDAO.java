package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface UserDAO extends GenericDAO<User> {

    @Override
    boolean create(User user) throws PersistException;

    @Override
    User find(int id) throws PersistException;

    @Override
    List<User> findAll() throws PersistException;

    @Override
    boolean update(User user) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (String login)
     * @return List<User>
     * @throws PersistException
     */
    @Override
    List<User> findAllByParameters(Object... params) throws PersistException;
}
