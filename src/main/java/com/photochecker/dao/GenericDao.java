package com.photochecker.dao;

import java.util.List;

/**
 * Created by market6 on 11.05.2017.
 */
public interface GenericDao<T> {
    public int save(T t);

    public T find(int id);

    public List<T> findAll();

    public boolean update(T t);

    public void remove(T t);
}
