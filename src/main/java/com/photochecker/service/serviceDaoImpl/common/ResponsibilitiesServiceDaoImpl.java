package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.Responsibility;
import com.photochecker.service.common.ResponsibilitiesService;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class ResponsibilitiesServiceDaoImpl implements ResponsibilitiesService {

    private ResponsibilityDao responsibilityDao;

    public ResponsibilitiesServiceDaoImpl() {
        responsibilityDao = DaoFactory.getResponsibilityDAO();
    }

    @Override
    public List<Responsibility> getAllResponsibilities() {
        List<Responsibility> responsibilities = responsibilityDao.findAll();
        return responsibilities;
    }

    @Override
    public boolean writeResponsibilities(List<Responsibility> respList) {
        for (Responsibility responsibility : respList) {
            responsibilityDao.update(responsibility);
        }
        return true;
    }
}
