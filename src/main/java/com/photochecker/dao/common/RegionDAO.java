package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.Region;
import com.photochecker.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface RegionDAO extends GenericDAO<Region> {

    @Override
    boolean create(Region region) throws PersistException;

    @Override
    Region find(int id) throws PersistException;

    @Override
    List<Region> findAll() throws PersistException;

    @Override
    boolean update(Region region) throws PersistException;

    @Override
    List<Region> findAllByParameters(Object... params) throws PersistException;
}
