package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.lka.LkaCriteriasDao;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.service.lka.LkaCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class LkaCriteriasServiceDaoImpl implements LkaCriteriasService {

    @Autowired
    private LkaCriteriasDao lkaCriteriasDao;

    @Override
    public LkaCriterias getLkaCriterias(int lkaId) {
        LkaCriterias lkaCriterias = lkaCriteriasDao.find(lkaId);
        return lkaCriterias;
    }

    @Override
    public List<LkaCriterias> getAllLkaCriterias() {
        List<LkaCriterias> lkaCriteriasList = lkaCriteriasDao.findAll();
        return lkaCriteriasList;
    }

    @Override
    public boolean writeNewLkaCriterias(List<LkaCriterias> critList) {
        boolean succeed;
        List<LkaCriterias> savedCriteriasList = lkaCriteriasDao.findAll();
        int id = -1;
        for (LkaCriterias lkaCriterias : critList) {
            if (savedCriteriasList.contains(lkaCriterias)) {
                lkaCriteriasDao.update(lkaCriterias);
            } else {
                id = lkaCriteriasDao.save(lkaCriterias);
            }
        }

        for (LkaCriterias lkaCriterias : savedCriteriasList) {
            if (!critList.contains(lkaCriterias)) {
                lkaCriteriasDao.remove(lkaCriterias);
            }
        }

        succeed = true;
        return succeed;
    }


}
