package com.photochecker.servlets;

import com.photochecker.model.*;
import com.photochecker.service.ServiceFactory;
import com.photochecker.service.common.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by market6 on 05.04.2017.
 */
@WebServlet(name = "LoginServlet",
        urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = ServiceFactory.getServiceFactory().getUserService();
    }

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

        User user = userService.loginUser(login, password);

        if (null == user) {
            request.setAttribute("error", true);
            request.setAttribute("pageTitle", "Авторизация");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
