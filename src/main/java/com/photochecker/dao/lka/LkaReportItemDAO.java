package com.photochecker.dao.lka;

import com.photochecker.model.lka.LkaReportItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 05.05.2017.
 */
public interface LkaReportItemDAO {
    public List<LkaReportItem> findAllByDate(LocalDate dateFrom,LocalDate dateTo, int repType);
}
