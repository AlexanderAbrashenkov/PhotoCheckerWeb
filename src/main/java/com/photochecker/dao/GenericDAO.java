package com.photochecker.dao;

import com.photochecker.model.PersistException;

import java.util.List;

/**
 * Created by market6 on 11.05.2017.
 */
public interface GenericDAO<T> {
    public boolean create(T t) throws PersistException;
    public T find(int id) throws PersistException;
    public List<T> findAll() throws PersistException;
    public boolean update(T t) throws PersistException;
    public List<T> findAllByParameters(Object... params) throws PersistException;
}
