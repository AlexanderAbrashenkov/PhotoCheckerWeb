package com.photochecker.dao;

import com.photochecker.dao.common.*;
import com.photochecker.dao.jdbc.spring.common.*;
import com.photochecker.dao.jdbc.spring.lka.ClientCriteriasDaoSpringImpl;
import com.photochecker.dao.jdbc.spring.lka.LkaCriteriasDaoSpringImpl;
import com.photochecker.dao.jdbc.spring.lka.LkaReportItemDaoSpringImpl;
import com.photochecker.dao.lka.ClientCriteriasDao;
import com.photochecker.dao.lka.LkaCriteriasDao;
import com.photochecker.dao.lka.LkaReportItemDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by market6 on 27.04.2017.
 */
public class DaoFactory {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

    public static RegionDao getRegionDAO() {
        return context.getBean(RegionDaoSpringImpl.class);
    };

    public static DistrDao getDistrDAO() {
        return context.getBean(DistrDaoSpringImpl.class);
    };

    public static LkaDao getLkaDAO() {
        return context.getBean(LkaDaoSpringImpl.class);
    };

    public static ClientCardDao getClientCardDAO() {
        return context.getBean(ClientCardDaoSpringImpl.class);
    };

    public static PhotoCardDao getPhotoCardDAO() {
        return context.getBean(PhotoCardDaoSpringImpl.class);
    };

    public static ReportTypeDao getReportTypeDAO() {
        return context.getBean(ReportTypeDaoSpringImpl.class);
    };

    public static ResponsibilityDao getResponsibilityDAO() {
        return context.getBean(ResponsibilityDaoSpringImpl.class);
    };

    public static UserDao getUserDAO() {
        return context.getBean(UserDaoSpringImpl.class);
    };



    public static ClientCriteriasDao getClientCriteriasDAO() {
        return context.getBean(ClientCriteriasDaoSpringImpl.class);
    };

    public static LkaCriteriasDao getLkaCriteriasDAO() {
        return context.getBean(LkaCriteriasDaoSpringImpl.class);
    };

    public static LkaReportItemDao getLkaReportItemDAO() {
        return context.getBean(LkaReportItemDaoSpringImpl.class);
    };
}
