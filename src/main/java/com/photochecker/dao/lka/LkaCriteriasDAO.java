package com.photochecker.dao.lka;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.Lka;
import com.photochecker.model.PersistException;
import com.photochecker.model.lka.LkaCriterias;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface LkaCriteriasDAO extends GenericDAO<LkaCriterias> {
    @Override
    boolean create(LkaCriterias lkaCriterias) throws PersistException;

    @Override
    LkaCriterias find(int id) throws PersistException;

    @Override
    List<LkaCriterias> findAll() throws PersistException;

    @Override
    boolean update(LkaCriterias lkaCriterias) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (**Doesn't specified yet**)
     * @return List<LkaCriterias>
     * @throws PersistException
     */
    @Override
    List<LkaCriterias> findAllByParameters(Object... params) throws PersistException;
}
