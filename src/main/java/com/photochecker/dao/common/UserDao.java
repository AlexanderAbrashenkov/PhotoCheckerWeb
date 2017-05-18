package com.photochecker.dao.common;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface UserDao extends GenericDao<User> {

    List<User> findAllByLogin(String login);

    User checkLoginAndPassword(String login, String password);
}
