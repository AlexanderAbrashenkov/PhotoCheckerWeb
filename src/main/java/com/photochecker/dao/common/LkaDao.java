package com.photochecker.dao.common;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.common.Distr;
import com.photochecker.model.common.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface LkaDao extends GenericDao<Lka> {

    List<Lka> findAllByDistrAndDates(Distr distr, LocalDate startDate, LocalDate endDate, int repTypeInd);
}
