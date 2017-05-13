package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.Distr;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface DistrDAO extends GenericDAO<Distr> {

    List<Distr> findAllByDates(LocalDate startDate, LocalDate endDate);
}
