package com.photochecker.dao;

import com.photochecker.dao.lka.ClientCriteriasDAO;
import com.photochecker.dao.lka.LkaCriteriasDAO;
import com.photochecker.model.ClientCard;
import com.photochecker.model.ReportType;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by market6 on 27.04.2017.
 */
public abstract class DAOFactory {

    public static final int MYSQL = 1;
    private static DAOFactory daoFactory = createDAOFactory();

    public abstract RegionDAO getRegionDAO();
    public abstract DistrDAO getDistrDAO();
    public abstract LkaDAO getLkaDAO();
    public abstract ClientCardDAO getClientCardDAO();
    public abstract PhotoCardDAO getPhotoCardDAO();
    public abstract ReportTypeDAO getReportTypeDAO();
    public abstract ResponsibilityDAO getResponsibilityDAO();
    public abstract UserDAO getUserDAO();

    public abstract ClientCriteriasDAO getClientCriteriasDAO();
    public abstract LkaCriteriasDAO getLkaCriteriasDAO();

    public static DAOFactory getDAOFactory() {
        return daoFactory;
    }

    private static DAOFactory createDAOFactory() {
        DAOFactory daoFactory = null;
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            int daoType = (int) env.lookup("DAOType");
            switch (daoType) {
                case MYSQL:
                    daoFactory = new DAOFactoryMySqlImpl();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return daoFactory;
    }
}
