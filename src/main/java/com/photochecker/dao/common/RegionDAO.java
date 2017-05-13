package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.Region;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface RegionDAO extends GenericDAO<Region> {

    List<Region> findAllByDates(LocalDate startDate, LocalDate endDate);
}
