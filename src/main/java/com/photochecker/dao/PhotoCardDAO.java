package com.photochecker.dao;

import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface PhotoCardDAO {
    public boolean create(PhotoCard photoCard);
    public PhotoCard find(String url);
    public List<PhotoCard> findAll();
    public List<PhotoCard> findAllByDateClientRepType(ReportType reportType, int clientId, LocalDate startDate, LocalDate endDate);
}
