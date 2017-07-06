package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstFormatDao;
import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstFormat;
import com.photochecker.model.nst.NstResp;
import com.photochecker.service.nst.NstFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NstFormatServiceDaoImpl implements NstFormatService {

    @Autowired
    private NstFormatDao nstFormatDao;
    @Autowired
    private NstRespDao nstRespDao;

    @Override
    public List<NstFormat> getNstFormats(User user, LocalDate startDate, LocalDate endDate, int repTypeIndex) {

        List<NstFormat> allNstFormats = nstFormatDao.findAllByDates(startDate, endDate, repTypeIndex);

        if (user.getRole() == 1) {
            List<NstResp> nstRespList = nstRespDao.findAllByUser(user);

            List<NstFormat> allowedFormats = nstRespList.stream()
                    .map(resp -> resp.getNstFormat())
                    .distinct()
                    .collect(Collectors.toList());
            allNstFormats.removeIf(nstFormat -> !allowedFormats.contains(nstFormat));
        }

        return allNstFormats;
    }
}
