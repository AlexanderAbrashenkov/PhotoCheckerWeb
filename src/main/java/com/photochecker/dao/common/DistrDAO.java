package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.Distr;
import com.photochecker.model.PersistException;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface DistrDAO extends GenericDAO<Distr> {

    @Override
    boolean create(Distr distr) throws PersistException;

    @Override
    Distr find(int id) throws PersistException;

    @Override
    List<Distr> findAll() throws PersistException;

    @Override
    boolean update(Distr distr) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (LocalDate startDate, LocalDate endDate)
     * @return List<Distr>
     * @throws PersistException
     */
    @Override
    List<Distr> findAllByParameters(Object... params) throws PersistException;
}
