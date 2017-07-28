package com.photochecker.service.nka;

import com.photochecker.model.nka.NkaClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public interface NkaClientCriteriasService {

    public boolean saveCriterias(NkaClientCriterias clientCriterias, List<String> photoUrlList);

    public NkaClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo);
}
