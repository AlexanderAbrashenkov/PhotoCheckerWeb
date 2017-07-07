package com.photochecker.dao.nka;

import com.photochecker.model.nka.NkaReportItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 07.07.2017.
 */
public interface NkaReportItemDao {
    List<NkaReportItem> findAllByDatesAndRepType(LocalDate dateFrom, LocalDate dateTo, int employeeId, int reportType);
}
