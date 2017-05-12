package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface PhotoCardDAO extends GenericDAO<PhotoCard> {

    @Override
    boolean create(PhotoCard photoCard) throws PersistException;

    @Override
    PhotoCard find(int id) throws PersistException;

    @Override
    List<PhotoCard> findAll() throws PersistException;

    @Override
    boolean update(PhotoCard photoCard) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (ReportType reportType, int clientId, LocalDate startDate, LocalDate endDate)
     * @return List<PhotoCard>
     * @throws PersistException
     */
    @Override
    List<PhotoCard> findAllByParameters(Object... params) throws PersistException;
}
