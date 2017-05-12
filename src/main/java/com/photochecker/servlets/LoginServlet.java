package com.photochecker.servlets;

import com.photochecker.dao.DAOFactory;
import com.photochecker.model.*;
import com.photochecker.service.MainService;

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
import java.sql.*;
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
            if (MainService.checkLogin(login)) {

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

                User user = new User(id, login, userName, userRole, null);

                List<ReportType> reportTypeList = null;
                try {
                    reportTypeList = DAOFactory.getDAOFactory().getReportTypeDAO().findAllByParameters(user);
                } catch (PersistException e) {
                    e.printStackTrace();
                }

                user.setReportTypeList(reportTypeList);

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
}
