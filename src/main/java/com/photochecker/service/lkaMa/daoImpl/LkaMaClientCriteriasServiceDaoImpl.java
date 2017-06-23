package com.photochecker.service.lkaMa.daoImpl;

import com.photochecker.dao.lkaMa.LkaMaClientCriteriasDao;
import com.photochecker.model.lkaMa.LkaMaClientCriterias;
import com.photochecker.service.lkaMa.LkaMaClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LkaMaClientCriteriasServiceDaoImpl implements LkaMaClientCriteriasService {
    @Autowired
    private LkaMaClientCriteriasDao lkaMaClientCriteriasDao;

    @Override
    public boolean saveCriterias(LkaMaClientCriterias lkaMaClientCriterias) {
        boolean succeed;
        int id = -1;
        List<LkaMaClientCriterias> savedClientCriterias = lkaMaClientCriteriasDao.findAllByClientAndDates(lkaMaClientCriterias.getClientId(),
                lkaMaClientCriterias.getDateFrom(), lkaMaClientCriterias.getDateTo());

        if (null != savedClientCriterias && savedClientCriterias.size() > 0) {
            succeed = lkaMaClientCriteriasDao.update(lkaMaClientCriterias);
        } else {
            id = lkaMaClientCriteriasDao.save(lkaMaClientCriterias);
            succeed = true;
        }

        return succeed;
    }

    @Override
    public LkaMaClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        LkaMaClientCriterias lkaMaClientCriterias = lkaMaClientCriteriasDao.findAllByClientAndDates(clientId, dateFrom, dateTo).get(0);
        return lkaMaClientCriterias;
    }
}
