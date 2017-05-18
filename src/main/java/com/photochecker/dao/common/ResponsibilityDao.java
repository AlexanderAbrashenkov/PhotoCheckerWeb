package com.photochecker.dao.common;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ResponsibilityDao extends GenericDao<Responsibility> {

    List<Responsibility> findAllByUser(User user);
}