package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.RegionDao;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.Region;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.service.lka.RegionService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 17.05.2017.
 */
public class RegionServiceDaoImpl implements RegionService {

    private RegionDao regionDao;
    private ResponsibilityDao responsibilityDao;

    public RegionServiceDaoImpl() {
        super();
        regionDao = DaoFactory.getDAOFactory().getRegionDAO();
        responsibilityDao = DaoFactory.getDAOFactory().getResponsibilityDAO();
    }

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
