package com.photochecker.dao.lkaMa;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.lkaMa.LkaMaClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface LkaMaClientCriteriasDao extends GenericDao<LkaMaClientCriterias> {

    List<LkaMaClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate);
}
