package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.service.lka.DistrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    /*public DistrServiceDaoImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        distrDao = (DistrDao) context.getBean("distrDao");
        responsibilityDao = (ResponsibilityDao) context.getBean("responsibilityDao");
    }*/

    @Override
    public List<Distr> getDistrs(User user, int regionId, LocalDate dateFrom, LocalDate dateTo) {
        List<Distr> allDistrs = distrDao.findAllByDates(dateFrom, dateTo);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);

            List<Distr> allowedDistrs = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 5)
                    .map(resp -> resp.getDistr())
                    .distinct()
                    .collect(Collectors.toList());
            allDistrs.removeIf(distr -> !allowedDistrs.contains(distr));
        }

        allDistrs.removeIf(distr -> distr.getRegion().getId() != regionId);
        return allDistrs;
    }
}
