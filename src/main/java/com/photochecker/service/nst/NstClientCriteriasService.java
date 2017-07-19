package com.photochecker.service.nst;

import com.photochecker.model.nst.NstClientCriterias;

import java.time.LocalDate;

public interface NstClientCriteriasService {

    /**
     * @return -1 - error while saving,
     * 0 - tt already was saved today,
     * 1 - tt already was saved earlier,
     * 2 - new tt saved today
     */
    int saveCriterias(NstClientCriterias nstClientCriterias, int formatId, int oblId);

    NstClientCriterias getSavedCriterias(int clientId, LocalDate startDate, LocalDate endDate);
}
