package com.photochecker.service.lkaMa;

import com.photochecker.model.lkaMa.LkaMaClientCriterias;

import java.time.LocalDate;

/**
 * Created by market6 on 17.05.2017.
 */
public interface LkaMaClientCriteriasService {

    public boolean saveCriterias(LkaMaClientCriterias clientCriterias);

    public LkaMaClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo);
}
