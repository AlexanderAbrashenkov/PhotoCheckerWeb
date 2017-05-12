package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.ClientCard;
import com.photochecker.model.Lka;
import com.photochecker.model.PersistException;
import com.photochecker.model.PhotoCard;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCardDAO extends GenericDAO<ClientCard> {
    @Override
    boolean create(ClientCard clientCard) throws PersistException;

    @Override
    ClientCard find(int id) throws PersistException;

    @Override
    List<ClientCard> findAll() throws PersistException;

    @Override
    boolean update(ClientCard clientCard) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (Lka lka, LocalDate startDate, LocalDate endDate)
     * @return List<ClientCard>
     * @throws PersistException
     */
    @Override
    List<ClientCard> findAllByParameters(Object... params) throws PersistException;
}
