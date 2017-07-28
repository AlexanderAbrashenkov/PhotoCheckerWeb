package com.photochecker.service.nka.daoImpl;

import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.nka.NkaClientCriteriasDao;
import com.photochecker.model.common.PhotoCard;
import com.photochecker.model.nka.NkaClientCriterias;
import com.photochecker.service.nka.NkaClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NkaClientCriteriasServiceDaoImpl implements NkaClientCriteriasService {

    @Autowired
    private NkaClientCriteriasDao nkaClientCriteriasDao;
    @Autowired
    private PhotoCardDao photoCardDao;

    @Override
    public boolean saveCriterias(NkaClientCriterias clientCriterias, List<String> photoUrlList) {
        boolean succeed;
        int id = -1;
        List<NkaClientCriterias> savedClientCriterias = nkaClientCriteriasDao.findAllByClientAndDates(clientCriterias.getClientId(),
                clientCriterias.getDateFrom(), clientCriterias.getDateTo());

        if (null != savedClientCriterias && savedClientCriterias.size() > 0) {
            succeed = nkaClientCriteriasDao.update(clientCriterias);
        } else {
            id = nkaClientCriteriasDao.save(clientCriterias);
            succeed = true;
        }

        photoCardDao.markCheckedByUrl(photoUrlList);

        return succeed;
    }

    @Override
    public NkaClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        NkaClientCriterias clientCriterias = nkaClientCriteriasDao.findAllByClientAndDates(clientId, dateFrom, dateTo).get(0);
        return clientCriterias;
    }
}
