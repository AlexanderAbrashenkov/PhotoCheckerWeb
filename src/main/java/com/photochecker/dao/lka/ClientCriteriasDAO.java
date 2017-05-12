package com.photochecker.dao.lka;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.lka.ClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCriteriasDAO extends GenericDAO<ClientCriterias> {
    @Override
    boolean create(ClientCriterias clientCriterias) throws PersistException;

    @Override
    ClientCriterias find(int id) throws PersistException;

    @Override
    List<ClientCriterias> findAll() throws PersistException;

    @Override
    boolean update(ClientCriterias clientCriterias) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (int clientId, LocalDate dateFrom, LocalDate dateTo)
     * @return List<ClientCriterias>
     * @throws PersistException
     */
    @Override
    List<ClientCriterias> findAllByParameters(Object... params) throws PersistException;
}
