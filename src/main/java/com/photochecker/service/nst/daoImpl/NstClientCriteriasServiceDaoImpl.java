package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstClientCriteriasDao;
import com.photochecker.model.nst.NstClientCriterias;
import com.photochecker.service.nst.NstClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NstClientCriteriasServiceDaoImpl implements NstClientCriteriasService {

    @Autowired
    private NstClientCriteriasDao nstClientCriteriasDao;

    @Override
    public boolean saveCriterias(NstClientCriterias nstClientCriterias) {
        boolean succeed;
        int id = -1;
        NstClientCriterias savedClientCriterias = nstClientCriteriasDao.findByClientAndDates(nstClientCriterias.getClientId(),
                nstClientCriterias.getDateFrom(), nstClientCriterias.getDateTo());

        if (null != savedClientCriterias) {
            succeed = nstClientCriteriasDao.update(nstClientCriterias);
        } else {
            id = nstClientCriteriasDao.save(nstClientCriterias);
            succeed = true;
        }

        return succeed;
    }

    @Override
    public NstClientCriterias getSavedCriterias(int clientId, LocalDate startDate, LocalDate endDate) {
        NstClientCriterias clientCriterias = nstClientCriteriasDao.findByClientAndDates(clientId, startDate, endDate);
        return clientCriterias;
    }
}
