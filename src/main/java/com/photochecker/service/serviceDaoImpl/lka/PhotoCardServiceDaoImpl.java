package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;
import com.photochecker.service.lka.PhotoCardService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class PhotoCardServiceDaoImpl implements PhotoCardService {
    private ReportTypeDao reportTypeDao;
    private PhotoCardDao photoCardDao;

    public PhotoCardServiceDaoImpl() {
        super();
        reportTypeDao = DaoFactory.getDAOFactory().getReportTypeDAO();
        photoCardDao = DaoFactory.getDAOFactory().getPhotoCardDAO();
    }

    @Override
    public List<PhotoCard> getPhotoList(int clientId, LocalDate dateFrom, LocalDate dateTo) {
            ReportType reportType = reportTypeDao.find(5);
            List<PhotoCard> photoCardList = photoCardDao.findAllByRepClientDates(reportType, clientId, dateFrom, dateTo);

            return photoCardList;
    }
}
