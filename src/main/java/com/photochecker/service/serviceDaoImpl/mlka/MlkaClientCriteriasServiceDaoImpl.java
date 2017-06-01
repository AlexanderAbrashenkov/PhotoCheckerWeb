package com.photochecker.service.serviceDaoImpl.mlka;

import com.photochecker.dao.mlka.MlkaClientCriteriasDao;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.model.mlka.MlkaClientCriterias;
import com.photochecker.service.mlka.MlkaClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public class MlkaClientCriteriasServiceDaoImpl implements MlkaClientCriteriasService {

    @Autowired
    private MlkaClientCriteriasDao mlkaClientCriteriasDao;

    @Override
    public boolean saveCriterias(MlkaClientCriterias clientCriterias) {
        boolean succeed;
        int id = -1;
        List<MlkaClientCriterias> savedClientCriterias = mlkaClientCriteriasDao.findAllByClientAndDates(clientCriterias.getClientId(),
                clientCriterias.getDateFrom(), clientCriterias.getDateTo());

        if (null != savedClientCriterias && savedClientCriterias.size() > 0) {
            succeed = mlkaClientCriteriasDao.update(clientCriterias);
        } else {
            id = mlkaClientCriteriasDao.save(clientCriterias);
            succeed = true;
        }

        return succeed;
    }

    @Override
    public MlkaClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        MlkaClientCriterias clientCriterias = mlkaClientCriteriasDao.findAllByClientAndDates(clientId, dateFrom, dateTo).get(0);
        return clientCriterias;
    }
}
