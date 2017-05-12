package com.photochecker.service;

import com.photochecker.dao.DAOFactory;
import com.photochecker.model.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by market6 on 27.04.2017.
 */
public class MainService {
    private static DAOFactory daoFactory = DAOFactory.getDAOFactory();

    public static LocalDate getInitialStartDate() {
        LocalDate startDate = LocalDate.now().minusDays(2);
        return startDate;
    }

    public static LocalDate getInitialEndDate() {
        LocalDate endDate = LocalDate.now().minusDays(2);
        return endDate;
    }

    public static List<Responsibility> getAllResponsibilities() {
        List<Responsibility> responsibilities = new ArrayList<>();
        try {
            responsibilities = daoFactory.getResponsibilityDAO().findAll();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return responsibilities;
    }

    public static Map<Integer, List<User>> getRespUsers() {
        Map<Integer, List<User>> result = new HashMap<>();
        try {
            List<ReportType> reportTypeList = daoFactory.getReportTypeDAO().findAll();
            List<User> userList = daoFactory.getUserDAO().findAll();

            for (ReportType reportType : reportTypeList) {
                List<User> repUserList = userList.stream()
                        .filter(user -> user.getRole() == 1 && user.getReportTypeList().contains(reportType))
                        .collect(Collectors.toList());
                result.put(reportType.getId(), repUserList);
            }
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean writeResponsibilities(List<Responsibility> respList) {
        boolean succeed = false;
        try {
            for (Responsibility responsibility : respList) {
                daoFactory.getResponsibilityDAO().update(responsibility);
            }
            succeed = true;
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return succeed;
    }

    public static List<ReportType> getReportTypes() {
        List<ReportType> reportTypeList = new ArrayList<>();
        try {
            reportTypeList = daoFactory.getReportTypeDAO().findAll();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return reportTypeList;
    }

    public static boolean checkLogin(String login) {
        List<User> userList = null;
        try {
            userList = daoFactory.getUserDAO().findAllByParameters(login);
        } catch (PersistException e) {
            e.printStackTrace();
        }

        if (null != userList && userList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
