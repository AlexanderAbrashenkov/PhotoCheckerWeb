package com.photochecker.dao.nka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.nka.NkaClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public interface NkaClientCriteriasDao extends GenericDao<NkaClientCriterias> {

    List<NkaClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate);
}
