package com.photochecker.dao.lkaDmp;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.lkaDmp.DmpReportItem;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 29.05.2017.
 */
public interface DmpReportItemDao extends GenericDao<DmpReportItem> {
    List<DmpReportItem> findAllByDatesAndRepType(LocalDate dateFrom, LocalDate dateTo, int i);
}
