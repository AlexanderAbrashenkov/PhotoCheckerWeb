package com.photochecker.service;

import com.photochecker.service.common.CommonService;
import com.photochecker.service.common.ReportTypeService;
import com.photochecker.service.common.ResponsibilitiesService;
import com.photochecker.service.common.UserService;
import com.photochecker.service.lka.*;
import com.photochecker.service.serviceDaoImpl.ServiceFactoryDaoImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by market6 on 17.05.2017.
 */
public abstract class ServiceFactory {
    public static final int DAO = 1;
    private static ServiceFactory serviceFactory = createServiceFactory();

    public static ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    private static ServiceFactory createServiceFactory() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServiceFactory serviceFactory = null;
        Properties properties = new Properties();

        try (InputStream in = classLoader.getResourceAsStream("config.properties")){
            properties.load(in);
            switch (properties.getProperty("service.type")) {
                case "DAO":
                    serviceFactory = new ServiceFactoryDaoImpl();
                    break;
                default:
                    serviceFactory = new ServiceFactoryDaoImpl();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serviceFactory;
    }


    public abstract CommonService getCommonService();

    public abstract ResponsibilitiesService getResponsibilitiesService();

    public abstract UserService getUserService();

    public abstract ReportTypeService getReportTypeService();



    public abstract RegionService getRegionService();

    public abstract DistrService getDistrService();

    public abstract LkaService getLkaService();

    public abstract ClientCardService getClientCardService();

    public abstract LkaCriteriasService getLkaCriteriasService();

    public abstract PhotoCardService getPhotoCardService();

    public abstract ClientCriteriasService getClientCriteriasService();

    public abstract ExcelReportService getExcelReportService();
}
