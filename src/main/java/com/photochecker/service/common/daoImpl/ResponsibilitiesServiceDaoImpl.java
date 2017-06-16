package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.common.Responsibility;
import com.photochecker.service.common.ResponsibilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
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
