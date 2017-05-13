package com.photochecker.dao;

import java.util.List;

/**
 * Created by market6 on 11.05.2017.
 */
public interface GenericDAO<T> {
    public boolean create(T t);

    public T find(int id);

    public List<T> findAll();

    public boolean update(T t);
}
