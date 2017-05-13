package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface PhotoCardDAO extends GenericDAO<PhotoCard> {

    List<PhotoCard> findAllByRepClientDates(ReportType reportType, int clientId, LocalDate startDate, LocalDate endDate);
}
