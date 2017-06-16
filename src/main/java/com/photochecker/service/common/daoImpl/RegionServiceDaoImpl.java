package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.RegionDao;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.model.common.Region;
import com.photochecker.model.common.Responsibility;
import com.photochecker.model.common.User;
import com.photochecker.model.mlka.NkaResp;
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
    @Autowired
    private NkaRespDao nkaRespDao;

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

    @Override
    public List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId) {
        List<Region> allRegions = regionDao.findAllByDatesAndNka(startDate, endDate, repTypeInd, nkaId);

        if (user.getRole() == 1) {
            List<NkaResp> nkaRespList = nkaRespDao.findAllByUser(user);

            List<Region> allowedRegions = nkaRespList.stream()
                    .map(nkaResp -> nkaResp.getDistr().getRegion())
                    .distinct()
                    .collect(Collectors.toList());
            allRegions.removeIf(region -> !allowedRegions.contains(region));
        }

        return allRegions;
    }
}
