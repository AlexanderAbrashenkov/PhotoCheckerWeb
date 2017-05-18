package com.photochecker.service.lka;

import com.photochecker.model.lka.ClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface ClientCriteriasService {

    public boolean saveCriterias(ClientCriterias clientCriterias);

    public ClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo);
}
