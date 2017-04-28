package com.photochecker.mysqlDAO;

import com.photochecker.dao.UserDAO;
import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class UserDAOMySqlImpl implements UserDAO {
    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
