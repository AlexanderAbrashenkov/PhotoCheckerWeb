package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.common.PhotoCard;
import com.photochecker.model.common.ReportType;
import com.photochecker.service.common.PhotoCardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class PhotoCardServiceDaoImpl implements PhotoCardService {
    @Autowired
    private ReportTypeDao reportTypeDao;
    @Autowired
    private PhotoCardDao photoCardDao;

    @Override
    public List<PhotoCard> getPhotoList(int clientId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd) {
            ReportType reportType = reportTypeDao.find(repTypeInd);
            List<PhotoCard> photoCardList = photoCardDao.findAllByRepClientDates(reportType, clientId, dateFrom, dateTo);

            return photoCardList;
    }
}
