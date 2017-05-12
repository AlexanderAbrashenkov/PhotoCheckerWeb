package com.photochecker.model.lka;

import com.photochecker.model.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by market6 on 24.03.2017.
 */
public class LkaExpert {

    public static boolean saveNewUser(User user, String pass) {
        boolean success = true;

        //todo: шифрование в отдельную model

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
            byte digest[] = messageDigest.digest(bytes1); //create code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String password = code.toString();

        try {
            Connection connection = DataSourcePhotochecker.getDataSource().getConnection();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO `users`\n" +
                    "(`user_login`, `user_pass`, `user_name`, `user_role`, `salt`)\n" +
                    "VALUES (?, ?, ?, ?, ?);");

            statement.setString(1, user.getUserLogin());
            statement.setString(2, password);
            statement.setString(3, user.getUserName());
            statement.setInt(4, user.getRole());
            statement.setString(5, salt);

            statement.execute();
            statement.close();

            statement = connection.prepareStatement("SELECT `id` FROM `users` WHERE `user_login` = ?");
            statement.setString(1, user.getUserLogin());
            ResultSet resultSet = statement.executeQuery();

            int id;

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            } else {
                throw new SQLException("User was not added");
            }

            statement = connection.prepareStatement("INSERT INTO `report_type_user` (`user_id`, `report_type`)\n" +
                    "VALUES (?, ?)");

            for (ReportType reportType : user.getReportTypeList()) {
                statement.setInt(1, id);
                statement.setInt(2, reportType.getId());
                statement.execute();
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }
}
