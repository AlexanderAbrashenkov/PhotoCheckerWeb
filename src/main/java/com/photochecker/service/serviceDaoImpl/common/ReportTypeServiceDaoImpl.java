package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.ReportType;
import com.photochecker.service.common.ReportTypeService;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class ReportTypeServiceDaoImpl implements ReportTypeService {

    private ReportTypeDao reportTypeDao;

    public ReportTypeServiceDaoImpl() {
        reportTypeDao = DaoFactory.getReportTypeDAO();
    }

    @Override
    public List<ReportType> getReportTypes() {
        List<ReportType> reportTypeList = reportTypeDao.findAll();
        return reportTypeList;
    }
}
