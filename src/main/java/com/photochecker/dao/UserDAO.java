package com.photochecker.dao;

import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface UserDAO {
    public boolean create(User user);
    public User find(int id);
    public List<User> findAll();
}
