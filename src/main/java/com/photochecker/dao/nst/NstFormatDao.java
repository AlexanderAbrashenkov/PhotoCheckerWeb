package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.nst.NstFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 05.07.2017.
 */
public interface NstFormatDao extends GenericDao<NstFormat> {
    List<NstFormat> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd);
}
