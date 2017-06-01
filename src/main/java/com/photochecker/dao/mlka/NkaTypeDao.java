package com.photochecker.dao.mlka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.mlka.NkaType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 31.05.2017.
 */
public interface NkaTypeDao extends GenericDao<NkaType> {

    List<NkaType> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd);
}
