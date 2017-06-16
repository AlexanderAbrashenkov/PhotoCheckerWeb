package com.photochecker.service.lkaDmp.daoImpl;

import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.lkaDmp.DmpClientCriteriasDao;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.service.lkaDmp.DmpClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DmpClientCriteriasServiceDaoImpl implements DmpClientCriteriasService {
    @Autowired
    private DmpClientCriteriasDao dmpClientCriteriasDao;
    @Autowired
    private PhotoCardDao photoCardDao;

    @Override
    public boolean saveCriterias(List<DmpClientCriterias> clientCriteriasList, List<String> photoUrlList) {
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

        photoCardDao.markCheckedByUrl(photoUrlList);
        return true;
    }

    @Override
    public List<DmpClientCriterias> getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        List<DmpClientCriterias> result = dmpClientCriteriasDao.findAllByClientAndDates(clientId, dateFrom, dateTo);
        return result;
    }
}
