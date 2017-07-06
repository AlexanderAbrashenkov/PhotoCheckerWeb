package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstClientCardDao;
import com.photochecker.model.nst.NstClientCard;
import com.photochecker.service.nst.NstClientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NstClientCardServiceDaoImpl implements NstClientCardService {

    @Autowired
    private NstClientCardDao nstClientCardDao;

    @Override
    public List<NstClientCard> getClientCardList(int formatId, int nstOblId, LocalDate startDate, LocalDate endDate, int repTypeIndex) {
        return nstClientCardDao.findAllByOblAndDates(formatId, nstOblId, startDate, endDate, repTypeIndex);
    }
}
