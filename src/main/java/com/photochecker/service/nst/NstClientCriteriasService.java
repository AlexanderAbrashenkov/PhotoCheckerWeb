package com.photochecker.service.nst;

import com.photochecker.model.nst.NstClientCriterias;

import java.time.LocalDate;

public interface NstClientCriteriasService {

    boolean saveCriterias(NstClientCriterias nstClientCriterias);

    NstClientCriterias getSavedCriterias(int clientId, LocalDate startDate, LocalDate endDate);
}
