package com.photochecker.dao.lkaMa;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.lkaMa.LkaMaReportItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 05.05.2017.
 */
public interface LkaMaReportItemDao {

    List<LkaMaReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType);
}
