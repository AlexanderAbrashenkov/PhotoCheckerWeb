package com.photochecker.servlets.admin;

import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.service.ServiceFactory;
import com.photochecker.service.common.ResponsibilitiesService;
import com.photochecker.service.common.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 20.04.2017.
 */
@WebServlet(name = "ResponsibServlet",
urlPatterns = "/reports/responsib")
public class ResponsibServlet extends HttpServlet {

    private ResponsibilitiesService responsibilitiesService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        responsibilitiesService = ServiceFactory.getServiceFactory().getResponsibilitiesService();
        userService = ServiceFactory.getServiceFactory().getUserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Responsibility> responsibilitiesList = responsibilitiesService.getAllResponsibilities();
        Map<Integer, List<User>> respUsers = userService.getRespUsers();
        request.setAttribute("respList", responsibilitiesList);
        request.setAttribute("respUsers", respUsers);
        request.setAttribute("pageTitle", "Ответственные");
        request.setAttribute("pageCategory", "administrate");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/responsibilitiesPage.jsp");
        dispatcher.forward(request, response);
    }
}
