package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.common.UserDao;
import com.photochecker.model.common.ReportType;
import com.photochecker.model.common.User;
import com.photochecker.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserServiceDaoImpl implements UserService {

    @Autowired
    private ReportTypeDao reportTypeDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, List<User>> getRespUsers() {
        Map<String, List<User>> result = new HashMap<>();
        List<ReportType> reportTypeList = reportTypeDao.findAll();
        List<User> userList = userDao.findAll();

        for (ReportType reportType : reportTypeList) {
            List<User> repUserList = userList.stream()
                    .filter(user -> user.getRole() == 1 && user.getReportTypeList().contains(reportType))
                    .collect(Collectors.toList());
            result.put(Integer.toString(reportType.getId()), repUserList);
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

    @Override
    public void saveNewUser(User user, String pass) {
        boolean success = true;

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);

        StringBuffer saltBuffer = new StringBuffer();

        for (byte b : bytes) {
            saltBuffer.append(Integer.toHexString(0x0100 + (b & 0x00FF)).substring(1));
        }

        String salt = saltBuffer.toString();

        String passwordSalt = pass + salt;

        StringBuffer code = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] bytes1 = passwordSalt.getBytes();
            byte digest[] = messageDigest.digest(bytes1); //save code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String password = code.toString();

        int id = userDao.saveNewUser(user, password, salt);

        user.setId(id);

        userDao.saveUserReports(user);
    }
}
