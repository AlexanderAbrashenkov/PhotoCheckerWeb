package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.common.UserDao;
import com.photochecker.model.ReportType;
import com.photochecker.model.User;
import com.photochecker.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by market6 on 17.05.2017.
 */
public class UserServiceDaoImpl implements UserService {

    @Autowired
    private ReportTypeDao reportTypeDao;
    @Autowired
    private UserDao userDao;

    /*public UserServiceDaoImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        reportTypeDao = (ReportTypeDao) context.getBean("reportTypeDao");
        userDao = (UserDao) context.getBean("userDao");
    }*/

    @Override
    public Map<Integer, List<User>> getRespUsers() {
        Map<Integer, List<User>> result = new HashMap<>();
        List<ReportType> reportTypeList = reportTypeDao.findAll();
        List<User> userList = userDao.findAll();

        for (ReportType reportType : reportTypeList) {
            List<User> repUserList = userList.stream()
                    .filter(user -> user.getRole() == 1 && user.getReportTypeList().contains(reportType))
                    .collect(Collectors.toList());
            result.put(reportType.getId(), repUserList);
        }

        return result;
    }

    @Override
    public boolean checkLogin(String login) {
        List<User> userList = userDao.findAllByLogin(login);

        if (null != userList && userList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User loginUser(String login, String password) {
        User user = null;
        if (checkLogin(login)) {
            user = userDao.checkLoginAndPassword(login, password);
        }
        return user;
    }
}
