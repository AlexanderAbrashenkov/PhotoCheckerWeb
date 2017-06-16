package com.photochecker.service.lkaDmp;

import com.photochecker.model.lkaDmp.DmpClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 26.05.2017.
 */
public interface DmpClientCriteriasService {
    public boolean saveCriterias(List<DmpClientCriterias> clientCriteriasList, List<String> photoUrlList);

    public List<DmpClientCriterias> getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo);
}
