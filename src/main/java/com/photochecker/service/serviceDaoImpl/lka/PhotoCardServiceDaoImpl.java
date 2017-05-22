package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;
import com.photochecker.service.lka.PhotoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

   /* public PhotoCardServiceDaoImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        reportTypeDao = (ReportTypeDao) context.getBean("reportTypeDao");
        photoCardDao = (PhotoCardDao) context.getBean("photoCardDao");
    }*/

    @Override
    public List<PhotoCard> getPhotoList(int clientId, LocalDate dateFrom, LocalDate dateTo) {
            ReportType reportType = reportTypeDao.find(5);
            List<PhotoCard> photoCardList = photoCardDao.findAllByRepClientDates(reportType, clientId, dateFrom, dateTo);

            return photoCardList;
    }
}
