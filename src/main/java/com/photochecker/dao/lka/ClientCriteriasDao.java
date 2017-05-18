package com.photochecker.dao.lka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.lka.ClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCriteriasDao extends GenericDao<ClientCriterias> {

    List<ClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate);
}
