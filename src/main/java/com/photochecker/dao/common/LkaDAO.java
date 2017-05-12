package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;
import com.photochecker.model.PersistException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface LkaDAO extends GenericDAO<Lka> {

    @Override
    boolean create(Lka lka) throws PersistException;

    @Override
    Lka find(int id) throws PersistException;

    @Override
    List<Lka> findAll() throws PersistException;

    @Override
    boolean update(Lka lka) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (Distr distr, LocalDate startDate, LocalDate endDate)
     * @return List<Lka>
     * @throws PersistException
     */
    @Override
    List<Lka> findAllByParameters(Object... params) throws PersistException;
}
