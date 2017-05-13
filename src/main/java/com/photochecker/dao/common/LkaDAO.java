package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.Distr;
import com.photochecker.model.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface LkaDAO extends GenericDAO<Lka> {

    List<Lka> findAllByDistrAndDates(Distr distr, LocalDate startDate, LocalDate endDate);
}
