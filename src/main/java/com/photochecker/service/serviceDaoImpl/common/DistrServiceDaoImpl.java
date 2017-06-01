package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.service.common.DistrService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 17.05.2017.
 */
public class DistrServiceDaoImpl implements DistrService {

    @Autowired
    private DistrDao distrDao;
    @Autowired
    private ResponsibilityDao responsibilityDao;
    @Autowired
    private NkaRespDao nkaRespDao;

    @Override
    public List<Distr> getDistrs(User user, int regionId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd) {
        List<Distr> allDistrs = distrDao.findAllByDates(dateFrom, dateTo, repTypeInd);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);

            List<Distr> allowedDistrs = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == repTypeInd)
                    .map(resp -> resp.getDistr())
                    .distinct()
                    .collect(Collectors.toList());
            allDistrs.removeIf(distr -> !allowedDistrs.contains(distr));
        }

        allDistrs.removeIf(distr -> distr.getRegion().getId() != regionId);
        return allDistrs;
    }

    @Override
    public List<Distr> getDistrs(User user, int regionId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId) {
        List<Distr> allDistrs = distrDao.findAllByDatesAndNka(startDate, endDate, repTypeInd, nkaId);

        if (user.getRole() == 1) {
            List<NkaResp> nkaRespList = nkaRespDao.findAllByUser(user);

            List<Distr> allowedDist = nkaRespList.stream()
                    .map(nkaResp -> nkaResp.getDistr())
                    .distinct()
                    .collect(Collectors.toList());
            allDistrs.removeIf(distr -> !allowedDist.contains(distr));
        }

        allDistrs.removeIf(distr -> distr.getRegion().getId() != regionId);
        return allDistrs;
    }
}
