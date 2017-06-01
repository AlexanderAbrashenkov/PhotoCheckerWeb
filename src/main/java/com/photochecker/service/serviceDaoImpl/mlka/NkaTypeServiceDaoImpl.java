package com.photochecker.service.serviceDaoImpl.mlka;

import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.dao.mlka.NkaTypeDao;
import com.photochecker.model.Region;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.model.mlka.NkaType;
import com.photochecker.service.mlka.NkaTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 31.05.2017.
 */
public class NkaTypeServiceDaoImpl implements NkaTypeService {

    @Autowired
    private NkaTypeDao nkaTypeDao;
    @Autowired
    private NkaRespDao nkaRespDao;

    @Override
    public List<NkaType> getNkaTypes(User user, LocalDate startDate, LocalDate endDate, int repTypeInd) {
        List<NkaType> allNkaTypes = nkaTypeDao.findAllByDates(startDate, endDate, repTypeInd);

        if (user.getRole() == 1) {
            List<NkaResp> nkaRespList = nkaRespDao.findAllByUser(user);

            List<NkaType> allowedNkaTypes = nkaRespList.stream()
                    .map(resp -> resp.getNkaType())
                    .distinct()
                    .collect(Collectors.toList());
            allNkaTypes.removeIf(nkaType -> !allowedNkaTypes.contains(nkaType));
        }

        return allNkaTypes;
    }
}
