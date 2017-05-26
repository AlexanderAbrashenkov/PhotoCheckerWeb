package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.Responsibility;
import com.photochecker.service.common.ResponsibilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class ResponsibilitiesServiceDaoImpl implements ResponsibilitiesService {

    @Autowired
    private ResponsibilityDao responsibilityDao;

    @Override
    public List<Responsibility> getAllResponsibilities() {
        List<Responsibility> responsibilities = responsibilityDao.findAll();
        Collections.sort(responsibilities);
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
