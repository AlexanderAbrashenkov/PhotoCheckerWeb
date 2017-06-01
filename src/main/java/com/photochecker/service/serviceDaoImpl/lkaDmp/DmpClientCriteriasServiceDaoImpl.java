package com.photochecker.service.serviceDaoImpl.lkaDmp;

import com.photochecker.dao.lkaDmp.DmpClientCriteriasDao;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.service.lkaDmp.DmpClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 26.05.2017.
 */
public class DmpClientCriteriasServiceDaoImpl implements DmpClientCriteriasService {
    @Autowired
    private DmpClientCriteriasDao dmpClientCriteriasDao;

    @Override
    public boolean saveCriterias(List<DmpClientCriterias> clientCriteriasList) {
        DmpClientCriterias dmpClientCriteriasFirst = clientCriteriasList.get(0);
        List<DmpClientCriterias> savedCrit = dmpClientCriteriasDao.findAllByClientAndDates(dmpClientCriteriasFirst.getClientId(),
                dmpClientCriteriasFirst.getDateFrom(),
                dmpClientCriteriasFirst.getDateTo());

        if (savedCrit.size() > 0) {
            dmpClientCriteriasDao.remove(dmpClientCriteriasFirst);
        }

        for (DmpClientCriterias dmpClientCriterias : clientCriteriasList) {
            dmpClientCriteriasDao.save(dmpClientCriterias);
        }
        return true;
    }

    @Override
    public List<DmpClientCriterias> getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        List<DmpClientCriterias> result = dmpClientCriteriasDao.findAllByClientAndDates(clientId, dateFrom, dateTo);
        return result;
    }
}
