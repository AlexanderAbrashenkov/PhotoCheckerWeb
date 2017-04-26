package com.photochecker.servlets.admin;

import com.photochecker.models.lka.LkaExpert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by market6 on 25.04.2017.
 */
@WebServlet(name = "CreateUserServlet",
        urlPatterns = "/reports/create_user")
public class CreateUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Integer, String> reportTypes = LkaExpert.getReportTypes();
        request.setAttribute("reportTypes", reportTypes);
        request.setAttribute("pageTitle", "Новый сотрудник");
        request.setAttribute("pageCategory", "administrate");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/createUserPage.jsp");
        dispatcher.forward(request, response);
    }
}
