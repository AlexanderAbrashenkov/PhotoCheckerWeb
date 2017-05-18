package com.photochecker.dao.jdbc.mysql;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.*;
import com.photochecker.dao.lka.ClientCriteriasDao;
import com.photochecker.dao.lka.LkaCriteriasDao;
import com.photochecker.dao.lka.LkaReportItemDao;
import com.photochecker.dao.jdbc.mysql.common.*;
import com.photochecker.dao.jdbc.mysql.lka.ClientCriteriasDaoMySqlImpl;
import com.photochecker.dao.jdbc.mysql.lka.LkaCriteriasDaoMySqlImpl;
import com.photochecker.dao.jdbc.mysql.lka.LkaReportItemDaoMySqlImpl;

/**
 * Created by market6 on 27.04.2017.
 */
public class DaoFactoryMySqlImpl extends DaoFactory {

    @Override
    public RegionDao getRegionDAO() {
        return new RegionDaoMySqlImpl();
    }

    @Override
    public DistrDao getDistrDAO() {
        return new DistrDaoMySqlImpl();
    }

    @Override
    public LkaDao getLkaDAO() {
        return new LkaDaoMySqlImpl();
    }

    @Override
    public ClientCardDao getClientCardDAO() {
        return new ClientCardDaoMySqlImpl();
    }

    @Override
    public PhotoCardDao getPhotoCardDAO() {
        return new PhotoCardDaoMySqlImpl();
    }

    @Override
    public ReportTypeDao getReportTypeDAO() {
        return new ReportTypeDaoMySqlImpl();
    }

    @Override
    public ResponsibilityDao getResponsibilityDAO() {
        return new ResponsibilityDaoMySqlImpl();
    }

    @Override
    public UserDao getUserDAO() {
        return new UserDaoMySqlImpl();
    }

    @Override
    public ClientCriteriasDao getClientCriteriasDAO() {
        return new ClientCriteriasDaoMySqlImpl();
    }

    @Override
    public LkaCriteriasDao getLkaCriteriasDAO() {
        return new LkaCriteriasDaoMySqlImpl();
    }

    @Override
    public LkaReportItemDao getLkaReportItemDAO() {
        return new LkaReportItemDaoMySqlImpl();
    }
}
