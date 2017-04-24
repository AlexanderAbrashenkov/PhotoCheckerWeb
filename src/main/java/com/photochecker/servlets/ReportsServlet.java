package com.photochecker.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by market6 on 06.04.2017.
 */
@WebServlet(name = "ReportsServlet",
urlPatterns = "/reports")
public class ReportsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pageTitle", "Разделы");
        request.setAttribute("pageCategory", "reports");
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/reportsPage.jsp");
        dispatcher.forward(request, response);
    }
}
