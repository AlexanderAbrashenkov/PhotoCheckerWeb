package com.photochecker.dao.lkaDmp;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.lkaDmp.DmpClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 26.05.2017.
 */
public interface DmpClientCriteriasDao extends GenericDao<DmpClientCriterias> {
    List<DmpClientCriterias> findAllByClientAndDates(int clientId, LocalDate startDate, LocalDate endDate);
}
