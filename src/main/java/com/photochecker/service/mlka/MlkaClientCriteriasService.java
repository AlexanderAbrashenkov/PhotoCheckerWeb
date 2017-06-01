package com.photochecker.service.mlka;

import com.photochecker.model.mlka.MlkaClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public interface MlkaClientCriteriasService {

    public boolean saveCriterias(MlkaClientCriterias clientCriterias);

    public MlkaClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo);
}
