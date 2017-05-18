package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;
import com.photochecker.service.lka.LkaService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class LkaServiceDaoImpl implements LkaService {

    private DistrDao distrDao;
    private LkaDao lkaDao;

    public LkaServiceDaoImpl() {
        super();
        distrDao = DaoFactory.getDAOFactory().getDistrDAO();
        lkaDao = DaoFactory.getDAOFactory().getLkaDAO();
    }

    @Override
    public List<Lka> getLkas(int distrId, LocalDate dateFrom, LocalDate dateTo) {
        Distr distr = distrDao.find(distrId);
        List<Lka> allLka = lkaDao.findAllByDistrAndDates(distr, dateFrom, dateTo);

        return allLka;
    }

    @Override
    public Lka getLkaById(int id) {
        Lka lka = lkaDao.find(id);
        return lka;
    }
}
