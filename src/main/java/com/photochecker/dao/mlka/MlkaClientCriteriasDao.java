package com.photochecker.dao.mlka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.mlka.MlkaClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public interface MlkaClientCriteriasDao extends GenericDao<MlkaClientCriterias> {

    List<MlkaClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate);
}
