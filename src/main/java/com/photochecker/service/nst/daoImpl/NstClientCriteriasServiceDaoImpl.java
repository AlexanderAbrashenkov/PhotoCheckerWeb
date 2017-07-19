package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstClientCriteriasDao;
import com.photochecker.dao.nst.NstStatDao;
import com.photochecker.model.nst.NstClientCriterias;
import com.photochecker.service.nst.NstClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NstClientCriteriasServiceDaoImpl implements NstClientCriteriasService {

    @Autowired
    private NstClientCriteriasDao nstClientCriteriasDao;
    @Autowired
    private NstStatDao nstStatDao;

    /**
     * @return int -1 - error while saving
     * 0 - tt already was saved today
     * 1 - tt already was saved earlier
     * 2 - new tt saved today
     */
    @Override
    public int saveCriterias(NstClientCriterias nstClientCriterias, int formatId, int oblId) {
        int answer = -1;
        NstClientCriterias savedClientCriterias = nstClientCriteriasDao.findByClientAndDates(nstClientCriterias.getClientId(),
                nstClientCriterias.getDateFrom(), nstClientCriterias.getDateTo());

        if (null != savedClientCriterias) {
            if (savedClientCriterias.getSaveDate().toLocalDate().isEqual(LocalDate.now())) {
                answer = 0;
            } else {
                answer = 1;
                nstStatDao.increaseCheckedToday(formatId, oblId, nstClientCriterias.getDateFrom(), nstClientCriterias.getDateTo());
            }
            nstClientCriteriasDao.update(nstClientCriterias);
        } else {
            nstClientCriteriasDao.save(nstClientCriterias);
            answer = 2;
            nstStatDao.increaseChecked(formatId, oblId, nstClientCriterias.getDateFrom(), nstClientCriterias.getDateTo());
        }

        return answer;
    }

    @Override
    public NstClientCriterias getSavedCriterias(int clientId, LocalDate startDate, LocalDate endDate) {
        NstClientCriterias clientCriterias = nstClientCriteriasDao.findByClientAndDates(clientId, startDate, endDate);
        return clientCriterias;
    }
}
