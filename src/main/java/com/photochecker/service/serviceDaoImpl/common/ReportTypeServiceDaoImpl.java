package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.ReportType;
import com.photochecker.service.common.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class ReportTypeServiceDaoImpl implements ReportTypeService {

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Override
    public List<ReportType> getReportTypes() {
        List<ReportType> reportTypeList = reportTypeDao.findAll();
        return reportTypeList;
    }
}
