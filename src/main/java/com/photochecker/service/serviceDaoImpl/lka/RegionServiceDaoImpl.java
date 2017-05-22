package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.common.RegionDao;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.Region;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.service.lka.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 17.05.2017.
 */
public class RegionServiceDaoImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;
    @Autowired
    private ResponsibilityDao responsibilityDao;

    /*public RegionServiceDaoImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        regionDao = (RegionDao) context.getBean("regionDao");
        responsibilityDao = (ResponsibilityDao) context.getBean("responsibilityDao");
    }*/

    @Override
    public List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate) {
        List<Region> allRegions = regionDao.findAllByDates(startDate, endDate);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);

            List<Region> allowedRegions = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 5)
                    .map(resp -> resp.getDistr().getRegion())
                    .distinct()
                    .collect(Collectors.toList());
            allRegions.removeIf(region -> !allowedRegions.contains(region));
        }

        return allRegions;
    }
}
