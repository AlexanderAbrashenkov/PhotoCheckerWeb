package com.photochecker.servlets.lka.ajax_servlets;

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
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 27.03.2017.
 */
@WebServlet(
        name = "downloadRegions",
        urlPatterns = "/reports/lka/getRegions"
)
public class DownloadRegionsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);
        dateTo = dateTo.plusDays(1);

        LkaExpert.setStartDate(dateFrom);
        LkaExpert.setEndDate(dateTo);

        Map<Integer, String> regions = LkaExpert.getRegionMap((User) request.getSession().getAttribute("user"));
        request.setAttribute("regionMap", regions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/regionOptions.jsp");
        dispatcher.forward(request, response);
    }
}
