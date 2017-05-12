package com.photochecker.dao.lka;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.lka.LkaReportItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 05.05.2017.
 */
public interface LkaReportItemDAO extends GenericDAO<LkaReportItem> {

    @Override
    boolean create(LkaReportItem lkaReportItem) throws PersistException;

    @Override
    LkaReportItem find(int id) throws PersistException;

    @Override
    List<LkaReportItem> findAll() throws PersistException;

    @Override
    boolean update(LkaReportItem lkaReportItem) throws PersistException;

    /**
     * Returns result according to params filter
     * @param params (LocalDate dateFrom, LocalDate dateTo, int repType)
     * @return List<LkaReportItem>
     * @throws PersistException
     */
    @Override
    List<LkaReportItem> findAllByParameters(Object... params) throws PersistException;
}
