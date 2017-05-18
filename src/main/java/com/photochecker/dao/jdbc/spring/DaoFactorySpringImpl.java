package com.photochecker.dao.jdbc.spring;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.*;
import com.photochecker.dao.jdbc.spring.common.*;
import com.photochecker.dao.lka.ClientCriteriasDao;
import com.photochecker.dao.lka.LkaCriteriasDao;
import com.photochecker.dao.lka.LkaReportItemDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by market6 on 18.05.2017.
 */
public class DaoFactorySpringImpl extends DaoFactory {
    private ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

    @Override
    public RegionDao getRegionDAO() {
        return context.getBean(RegionDaoSpringImpl.class);
    }

    @Override
    public DistrDao getDistrDAO() {
        return context.getBean(DistrDaoSpringImpl.class);
    }

    @Override
    public LkaDao getLkaDAO() {
        return context.getBean(LkaDaoSpringImpl.class);
    }

    @Override
    public ClientCardDao getClientCardDAO() {
        return context.getBean(ClientCardDaoSpringImpl.class);
    }

    @Override
    public PhotoCardDao getPhotoCardDAO() {
        return context.getBean(PhotoCardDaoSpringImpl.class);
    }

    @Override
    public ReportTypeDao getReportTypeDAO() {
        return context.getBean(ReportTypeDaoSpringImpl.class);
    }

    @Override
    public ResponsibilityDao getResponsibilityDAO() {
        return context.getBean(ResponsibilityDaoSpringImpl.class);
    }

    @Override
    public UserDao getUserDAO() {
        return context.getBean(UserDaoSpringImpl.class);
    }

    @Override
    public ClientCriteriasDao getClientCriteriasDAO() {
        return null;
    }

    @Override
    public LkaCriteriasDao getLkaCriteriasDAO() {
        return null;
    }

    @Override
    public LkaReportItemDao getLkaReportItemDAO() {
        return null;
    }
}
