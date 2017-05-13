package com.photochecker.service;

import com.photochecker.dao.DAOFactory;
import com.photochecker.model.ReportType;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Responsibility> responsibilities = daoFactory.getResponsibilityDAO().findAll();

        return responsibilities;
    }

    public static Map<Integer, List<User>> getRespUsers() {
        Map<Integer, List<User>> result = new HashMap<>();
        List<ReportType> reportTypeList = daoFactory.getReportTypeDAO().findAll();
        List<User> userList = daoFactory.getUserDAO().findAll();

        for (ReportType reportType : reportTypeList) {
            List<User> repUserList = userList.stream()
                    .filter(user -> user.getRole() == 1 && user.getReportTypeList().contains(reportType))
                    .collect(Collectors.toList());
            result.put(reportType.getId(), repUserList);
        }

        return result;
    }

    public static boolean writeResponsibilities(List<Responsibility> respList) {
        for (Responsibility responsibility : respList) {
            daoFactory.getResponsibilityDAO().update(responsibility);
        }
        return true;
    }

    public static List<ReportType> getReportTypes() {
        List<ReportType> reportTypeList = daoFactory.getReportTypeDAO().findAll();
        return reportTypeList;
    }

    public static boolean checkLogin(String login) {
        List<User> userList = daoFactory.getUserDAO().findAllByLogin(login);

        if (null != userList && userList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
