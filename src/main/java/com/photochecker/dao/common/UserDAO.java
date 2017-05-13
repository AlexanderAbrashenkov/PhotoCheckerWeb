package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface UserDAO extends GenericDAO<User> {

    List<User> findAllByLogin(String login);
}
