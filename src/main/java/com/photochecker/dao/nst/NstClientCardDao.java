package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.nst.NstClientCard;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 21.06.2017.
 */
public interface NstClientCardDao extends GenericDao<NstClientCard> {
    List<NstClientCard> findAllByOblAndDates(int formatId, int oblId, LocalDate startDate, LocalDate endDate, int repTypeInd);
}
