package com.photochecker.servlets;

import com.photochecker.models.DataSourcePhotochecker;
import com.photochecker.models.User;
import com.photochecker.models.WrongUserException;
import com.photochecker.models.lka.LkaExpert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 05.04.2017.
 */
@WebServlet(name = "LoginServlet",
urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getParameterMap().containsKey("login_login")) {
            request.setAttribute("error", false);
            request.setAttribute("pageTitle", "Авторизация");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;
        }


        String login = request.getParameter("login_login");
        String password = request.getParameter("login_password");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            if (LkaExpert.checkLogin(login)) {

                connection = DataSourcePhotochecker.getDataSource().getConnection();
                statement = connection.prepareStatement("SELECT * FROM `users` " +
                        "WHERE `user_login` = ? ");
                statement.setString(1, login);
                resultSet = statement.executeQuery();

                resultSet.next();

                int id = resultSet.getInt("id");
                String userName = resultSet.getString("user_name");
                String userPass = resultSet.getString("user_pass");
                String userSalt = resultSet.getString("salt");

                String passSalt = password + userSalt;
                StringBuffer code = new StringBuffer();
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                    byte bytes[] = passSalt.getBytes();
                    byte digest[] = messageDigest.digest(bytes); //create code
                    for (int i = 0; i < digest.length; ++i) {
                        code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                if (!code.toString().equals(userPass)) {
                    throw new WrongUserException();
                }

                int userRole = resultSet.getInt("user_role");

                resultSet.close();
                statement.close();

                statement = connection.prepareStatement("SELECT `report_type` FROM `report_type_user`\n" +
                        "WHERE `user_id` = ?");
                statement.setInt(1, id);
                resultSet = statement.executeQuery();

                List<Integer> reportTypeList = new ArrayList<>();

                while (resultSet.next()) {
                    reportTypeList.add(resultSet.getInt("report_type"));
                }

                User user = new User(id, login, userName, userRole, reportTypeList);

                resultSet.close();
                statement.close();
                connection.close();

                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                String lastUrl = (String) session.getAttribute("lastUrl");
                String dispatherPath;
                if (null == lastUrl) {
                    dispatherPath = "/";
                } else if (lastUrl.startsWith("/reports")) {
                    dispatherPath = "/reports";
                } else if (lastUrl.startsWith("/route")) {
                    dispatherPath = "/route";
                } else {
                    dispatherPath = "/";
                }
                response.sendRedirect(dispatherPath);

            } else {
                throw new WrongUserException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (WrongUserException e) {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            request.setAttribute("error", true);
            request.setAttribute("pageTitle", "Авторизация");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public static void main(String[] args) {
        String login = "user1";
        String password = "uXirUtfYa2";
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);

        StringBuffer saltBuffer = new StringBuffer();

        for (byte b : bytes) {
            saltBuffer.append(Integer.toHexString(0x0100 + (b & 0x00FF)).substring(1));
        }

        String salt = saltBuffer.toString();

        String passwordSalt = password + salt;

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

        System.err.println("login: " + login + ", password: " + password + ", salt: " + salt +
            ", passSalt: " + passwordSalt + ", hash: " + code.toString());
    }
}
