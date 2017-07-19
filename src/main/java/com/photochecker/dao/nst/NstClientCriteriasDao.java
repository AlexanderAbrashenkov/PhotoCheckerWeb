package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.nst.NstClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 21.06.2017.
 */
public interface NstClientCriteriasDao extends GenericDao<NstClientCriterias> {
    NstClientCriterias findByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate);

    List<NstClientCriterias> findAllByDates(LocalDate startDate, LocalDate endDate);

    int copyCritsToCommon(String saveTableName);

    boolean createCurrentTable(String saveTableName);
}
