package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.ReportType;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ReportTypeDAO extends GenericDAO<ReportType> {

    @Override
    boolean create(ReportType reportType) throws PersistException;

    @Override
    ReportType find(int id) throws PersistException;

    @Override
    List<ReportType> findAll() throws PersistException;

    @Override
    boolean update(ReportType reportType) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (User user)
     * @return List<ReportType>
     * @throws PersistException
     */
    @Override
    List<ReportType> findAllByParameters(Object... params) throws PersistException;
}
