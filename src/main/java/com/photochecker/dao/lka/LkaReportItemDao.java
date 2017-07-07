package com.photochecker.dao.lka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.lka.LkaReportItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 05.05.2017.
 */
public interface LkaReportItemDao {

    List<LkaReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType);
}
