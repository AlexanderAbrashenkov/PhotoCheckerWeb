package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;
import com.photochecker.service.lka.LkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class LkaServiceDaoImpl implements LkaService {

    @Autowired
    private DistrDao distrDao;
    @Autowired
    private LkaDao lkaDao;

    /*public LkaServiceDaoImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        distrDao = (DistrDao) context.getBean("distrDao");
        lkaDao = (LkaDao) context.getBean("lkaDao");
    }*/

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
