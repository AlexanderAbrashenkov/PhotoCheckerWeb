package com.photochecker.service.common;

import com.photochecker.model.common.User;

import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 17.05.2017.
 */
public interface UserService {
    public Map<String, List<User>> getRespUsers();

    public boolean checkLogin(String login);

    public User loginUser(String login, String password);

    public void saveNewUser(User user, String pass);
}
