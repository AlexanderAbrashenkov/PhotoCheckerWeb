package com.photochecker.servlets.admin;

import com.photochecker.models.Responsibility;
import com.photochecker.models.User;
import com.photochecker.models.lka.LkaCriterias;
import com.photochecker.models.lka.LkaExpert;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Responsibility> responsibilitiesList = LkaExpert.getAllResponsibilities();
        Map<Integer, List<User>> respUsers = LkaExpert.getRespUsers();
        request.setAttribute("respList", responsibilitiesList);
        request.setAttribute("respUsers", respUsers);
        request.setAttribute("pageTitle", "Ответственные");
        request.setAttribute("pageCategory", "administrate");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/responsibilitiesPage.jsp");
        dispatcher.forward(request, response);
    }
}
