package com.photochecker.service.lkaMa.daoImpl;

import com.photochecker.dao.lkaMa.LkaMaCriteriasDao;
import com.photochecker.model.lkaMa.LkaMaCriterias;
import com.photochecker.service.lkaMa.LkaMaCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LkaMaCriteriasServiceDaoImpl implements LkaMaCriteriasService {

    @Autowired
    private LkaMaCriteriasDao lkaMaCriteriasDao;

    @Override
    public LkaMaCriterias getLkaCriterias(int lkaId) {
        LkaMaCriterias lkaMaCriterias = lkaMaCriteriasDao.find(lkaId);
        return lkaMaCriterias;
    }

    @Override
    public List<LkaMaCriterias> getAllLkaCriterias() {
        List<LkaMaCriterias> lkaMaCriteriasList = lkaMaCriteriasDao.findAll();
        return lkaMaCriteriasList;
    }

    @Override
    public boolean writeNewLkaCriterias(List<LkaMaCriterias> critList) {
        boolean succeed;
        List<LkaMaCriterias> savedCriteriasList = lkaMaCriteriasDao.findAll();
        int id = -1;
        for (LkaMaCriterias lkaMaCriterias : critList) {
            if (savedCriteriasList.contains(lkaMaCriterias)) {
                lkaMaCriteriasDao.update(lkaMaCriterias);
            } else {
                id = lkaMaCriteriasDao.save(lkaMaCriterias);
            }
        }

        for (LkaMaCriterias lkaMaCriterias : savedCriteriasList) {
            if (!critList.contains(lkaMaCriterias)) {
                lkaMaCriteriasDao.remove(lkaMaCriterias);
            }
        }

        succeed = true;
        return succeed;
    }


}
