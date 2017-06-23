package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstOblDao;
import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.model.common.Region;
import com.photochecker.model.common.Responsibility;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstObl;
import com.photochecker.model.nst.NstResp;
import com.photochecker.service.nst.NstOblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NstOblServiceDaoImpl implements NstOblService {

    @Autowired
    private NstOblDao nstOblDao;
    @Autowired
    private NstRespDao nstRespDao;

    @Override
    public List<NstObl> getNstObls(User user, LocalDate startDate, LocalDate endDate, int repTypeIndex) {
        List<NstObl> allNstObls = nstOblDao.findAllByDates(startDate, endDate, repTypeIndex);

        if (user.getRole() == 1) {
            List<NstResp> nstRespList = nstRespDao.findAllByUser(user);

            List<NstObl> allowedObls = nstRespList.stream()
                    .map(resp -> resp.getNstObl())
                    .distinct()
                    .collect(Collectors.toList());
            allNstObls.removeIf(nstObl -> !allowedObls.contains(nstObl));
        }

        return allNstObls;
    }
}
