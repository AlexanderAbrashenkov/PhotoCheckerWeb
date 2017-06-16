package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.model.common.Distr;
import com.photochecker.model.common.Lka;
import com.photochecker.service.common.LkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LkaServiceDaoImpl implements LkaService {

    @Autowired
    private DistrDao distrDao;
    @Autowired
    private LkaDao lkaDao;

    @Override
    public List<Lka> getLkas(int distrId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd) {
        Distr distr = distrDao.find(distrId);
        List<Lka> allLka = lkaDao.findAllByDistrAndDates(distr, dateFrom, dateTo, repTypeInd);

        return allLka;
    }

    @Override
    public Lka getLkaById(int id) {
        Lka lka = lkaDao.find(id);
        return lka;
    }
}
