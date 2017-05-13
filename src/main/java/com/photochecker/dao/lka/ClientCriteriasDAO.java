package com.photochecker.dao.lka;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.lka.ClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCriteriasDAO extends GenericDAO<ClientCriterias> {

    List<ClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate);
}
