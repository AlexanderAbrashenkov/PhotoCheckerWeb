package com.photochecker.dao;

import com.photochecker.model.Responsibility;
import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ResponsibilityDAO {
    public boolean create(Responsibility responsibility);
    public List<Responsibility> find(User user);
    public List<Responsibility> findAll();
    public boolean update(Responsibility responsibility);
}