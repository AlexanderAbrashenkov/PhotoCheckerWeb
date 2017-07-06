package com.photochecker.service.nka;

import com.photochecker.model.nka.NkaClientCriterias;

import java.time.LocalDate;

/**
 * Created by market6 on 01.06.2017.
 */
public interface NkaClientCriteriasService {

    public boolean saveCriterias(NkaClientCriterias clientCriterias);

    public NkaClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo);
}
