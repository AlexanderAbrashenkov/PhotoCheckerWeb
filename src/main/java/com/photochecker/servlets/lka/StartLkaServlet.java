package com.photochecker.servlets.lka;

import com.photochecker.models.User;
import com.photochecker.models.lka.LkaExpert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by market6 on 21.03.2017.
 */
@WebServlet(
        name = "startLkaServlet",
        urlPatterns = "/reports/lka"
)
public class StartLkaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate startDate = LkaExpert.getInitialStartDate();
        LocalDate endDate = LkaExpert.getInitialEndDate();
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        endDate = endDate.plusDays(1);
        LkaExpert.setEndDate(endDate);
        request.setAttribute("regionMap", LkaExpert.getRegionMap((User) request.getSession().getAttribute("user")));
        request.setAttribute("pageTitle", "Фотоотчет LKA");
        request.setAttribute("pageCategory", "lka");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("../WEB-INF/view/lka/lkaPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
