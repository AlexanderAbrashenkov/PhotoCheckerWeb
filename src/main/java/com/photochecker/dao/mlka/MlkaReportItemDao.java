package com.photochecker.dao.mlka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.lka.LkaReportItem;
import com.photochecker.model.mlka.MlkaReportItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 05.05.2017.
 */
public interface MlkaReportItemDao {

    List<MlkaReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType);
}
