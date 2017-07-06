package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.nst.NstObl;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 21.06.2017.
 */
public interface NstOblDao extends GenericDao<NstObl> {
    List<NstObl> findAllByDates(LocalDate startDate, LocalDate endDate, int formatId, int repTypeInd);
}
