package com.photochecker.dao.common;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.common.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface UserDao extends GenericDao<User> {

    List<User> findAllByLogin(String login);

    User checkLoginAndPassword(String login, String password);

    int saveNewUser(User user, String password, String salt);

    void saveUserReports(User user);
}
