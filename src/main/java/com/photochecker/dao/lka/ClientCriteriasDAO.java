package com.photochecker.dao.lka;

import com.photochecker.model.lka.ClientCriterias;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCriteriasDAO {
    public boolean create(ClientCriterias clientCriterias);
    public ClientCriterias find(int clientId, LocalDate dateFrom, LocalDate dateTo);
    public List<ClientCriterias> findAll();
    public boolean update(ClientCriterias clientCriterias);
}
