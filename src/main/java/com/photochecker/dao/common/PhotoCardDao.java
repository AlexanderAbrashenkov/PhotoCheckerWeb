package com.photochecker.dao.common;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface PhotoCardDao extends GenericDao<PhotoCard> {

    List<PhotoCard> findAllByRepClientDates(ReportType reportType, int clientId, LocalDate startDate, LocalDate endDate);

    PhotoCard findByUrl(String url);
}
