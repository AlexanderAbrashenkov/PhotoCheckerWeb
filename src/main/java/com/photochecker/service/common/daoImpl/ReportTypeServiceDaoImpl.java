package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.common.ReportType;
import com.photochecker.service.common.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportTypeServiceDaoImpl implements ReportTypeService {

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Override
    public List<ReportType> getReportTypes() {
        List<ReportType> reportTypeList = reportTypeDao.findAll();
        return reportTypeList;
    }
}
