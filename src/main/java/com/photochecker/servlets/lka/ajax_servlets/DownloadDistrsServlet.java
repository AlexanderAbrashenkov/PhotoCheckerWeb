package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.models.lka.LkaExpert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by market6 on 27.03.2017.
 */
@WebServlet(
        name = "downloadDistrs",
        urlPatterns = "/reports/lka/getDistrs"
)
public class DownloadDistrsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int regionId = Integer.parseInt(request.getParameter("regionId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);
        dateTo = dateTo.plusDays(1);

        LkaExpert.setStartDate(dateFrom);
        LkaExpert.setEndDate(dateTo);

        Map<Integer, String> distrMap = LkaExpert.getDistrMap(regionId);
        request.setAttribute("distrMap", distrMap);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/distrOptions.jsp");
        dispatcher.forward(request, response);
    }
}
