package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.common.RegionDao;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.Region;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.service.common.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate, int repTypeInd) {
        List<Region> allRegions = regionDao.findAllByDates(startDate, endDate, repTypeInd);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);

            List<Region> allowedRegions = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == repTypeInd)
                    .map(resp -> resp.getDistr().getRegion())
                    .distinct()
                    .collect(Collectors.toList());
            allRegions.removeIf(region -> !allowedRegions.contains(region));
        }

        return allRegions;
    }
}
