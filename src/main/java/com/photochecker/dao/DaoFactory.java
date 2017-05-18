package com.photochecker.dao;

import com.photochecker.dao.common.*;
import com.photochecker.dao.jdbc.spring.DaoFactorySpringImpl;
import com.photochecker.dao.lka.ClientCriteriasDao;
import com.photochecker.dao.lka.LkaCriteriasDao;
import com.photochecker.dao.lka.LkaReportItemDao;
import com.photochecker.dao.jdbc.mysql.DaoFactoryMySqlImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by market6 on 27.04.2017.
 */
public abstract class DaoFactory {

    public static final int MYSQL = 1;
    public static final int SPRING = 2;

    private static DaoFactory daoFactory = createDAOFactory();

    public static DaoFactory getDAOFactory() {
        return daoFactory;
    }

    private static DaoFactory createDAOFactory() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        DaoFactory daoFactory = null;

        try (InputStream in = classLoader.getResourceAsStream("config.properties")) {
            properties.load(in);
            switch (properties.getProperty("data.storage")) {
                case "MySql":
                    daoFactory = new DaoFactoryMySqlImpl();
                    break;
                case "Spring":
                    daoFactory = new DaoFactorySpringImpl();
                    break;
                default:
                    daoFactory = new DaoFactoryMySqlImpl();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return daoFactory;
    }

    public abstract RegionDao getRegionDAO();

    public abstract DistrDao getDistrDAO();

    public abstract LkaDao getLkaDAO();

    public abstract ClientCardDao getClientCardDAO();

    public abstract PhotoCardDao getPhotoCardDAO();

    public abstract ReportTypeDao getReportTypeDAO();

    public abstract ResponsibilityDao getResponsibilityDAO();

    public abstract UserDao getUserDAO();



    public abstract ClientCriteriasDao getClientCriteriasDAO();

    public abstract LkaCriteriasDao getLkaCriteriasDAO();

    public abstract LkaReportItemDao getLkaReportItemDAO();
}
