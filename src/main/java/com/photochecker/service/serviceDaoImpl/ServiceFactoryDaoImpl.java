package com.photochecker.service.serviceDaoImpl;

import com.photochecker.service.ServiceFactory;
import com.photochecker.service.common.CommonService;
import com.photochecker.service.common.ReportTypeService;
import com.photochecker.service.common.ResponsibilitiesService;
import com.photochecker.service.common.UserService;
import com.photochecker.service.lka.*;
import com.photochecker.service.serviceDaoImpl.common.CommonServiceDaoImpl;
import com.photochecker.service.serviceDaoImpl.common.ReportTypeServiceDaoImpl;
import com.photochecker.service.serviceDaoImpl.common.ResponsibilitiesServiceDaoImpl;
import com.photochecker.service.serviceDaoImpl.common.UserServiceDaoImpl;
import com.photochecker.service.serviceDaoImpl.lka.*;

/**
 * Created by market6 on 17.05.2017.
 */
public class ServiceFactoryDaoImpl extends ServiceFactory {

    @Override
    public CommonService getCommonService() {
        return new CommonServiceDaoImpl();
    }

    @Override
    public ResponsibilitiesService getResponsibilitiesService() {
        return new ResponsibilitiesServiceDaoImpl();
    }

    @Override
    public UserService getUserService() {
        return new UserServiceDaoImpl();
    }

    @Override
    public ReportTypeService getReportTypeService() {
        return new ReportTypeServiceDaoImpl();
    }



    @Override
    public RegionService getRegionService() {
        return new RegionServiceDaoImpl();
    }

    @Override
    public DistrService getDistrService() {
        return new DistrServiceDaoImpl();
    }

    @Override
    public LkaService getLkaService() {
        return new LkaServiceDaoImpl();
    }

    @Override
    public ClientCardService getClientCardService() {
        return new ClientCardServiceDaoImpl();
    }

    @Override
    public LkaCriteriasService getLkaCriteriasService() {
        return new LkaCriteriasServiceDaoImpl();
    }

    @Override
    public PhotoCardService getPhotoCardService() {
        return new PhotoCardServiceDaoImpl();
    }

    @Override
    public ClientCriteriasService getClientCriteriasService() {
        return new ClientCriteriasServiceDaoImpl();
    }

    @Override
    public ExcelReportService getExcelReportService() {
        return new ExcelReportServiceDaoImpl();
    }
}
