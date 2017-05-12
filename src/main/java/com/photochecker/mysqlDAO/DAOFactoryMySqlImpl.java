package com.photochecker.mysqlDAO;

import com.photochecker.dao.*;
import com.photochecker.dao.common.*;
import com.photochecker.dao.lka.ClientCriteriasDAO;
import com.photochecker.dao.lka.LkaCriteriasDAO;
import com.photochecker.dao.lka.LkaReportItemDAO;
import com.photochecker.mysqlDAO.common.*;
import com.photochecker.mysqlDAO.lka.ClientCriteriasDAOMySqlImpl;
import com.photochecker.mysqlDAO.lka.LkaCriteriasDAOMySqlImpl;
import com.photochecker.mysqlDAO.lka.LkaReportItemDAOMySqlImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by market6 on 27.04.2017.
 */
public class DAOFactoryMySqlImpl extends DAOFactory {

    private static final Context CONTEXT = createContext();
    private static final DataSource DATA_SOURCE = createDataSource();

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

    private static Context createContext() {
        try {
            return new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DataSource createDataSource() {
        try {
            Context envContext = (Context) CONTEXT.lookup("java:/comp/env");
            return (DataSource) envContext.lookup("jdbc/library");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RegionDAO getRegionDAO() {
        return new RegionDAOMySqlImpl();
    }

    @Override
    public DistrDAO getDistrDAO() {
        return new DistrDAOMySqlImpl();
    }

    @Override
    public LkaDAO getLkaDAO() {
        return new LkaDAOMySqlImpl();
    }

    @Override
    public ClientCardDAO getClientCardDAO() {
        return new ClientCardDAOMySqlImpl();
    }

    @Override
    public PhotoCardDAO getPhotoCardDAO() {
        return new PhotoCardDAOMySqlImpl();
    }

    @Override
    public ReportTypeDAO getReportTypeDAO() {
        return new ReportTypeDAOMySqlImpl();
    }

    @Override
    public ResponsibilityDAO getResponsibilityDAO() {
        return new ResponsibilityDAOMySqlImpl();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMySqlImpl();
    }

    @Override
    public ClientCriteriasDAO getClientCriteriasDAO() {
        return new ClientCriteriasDAOMySqlImpl();
    }

    @Override
    public LkaCriteriasDAO getLkaCriteriasDAO() {
        return new LkaCriteriasDAOMySqlImpl();
    }

    @Override
    public LkaReportItemDAO getLkaReportItemDAO() {
        return new LkaReportItemDAOMySqlImpl();
    }
}
