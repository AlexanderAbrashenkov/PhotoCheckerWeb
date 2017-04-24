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
        name = "downloadLkas",
        urlPatterns = "/reports/lka/getLkas"
)
public class DownloadLkaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int regionId = Integer.parseInt(request.getParameter("regionId"));
        int distrId = Integer.parseInt(request.getParameter("distrId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);
        dateTo = dateTo.plusDays(1);

        LkaExpert.setStartDate(dateFrom);
        LkaExpert.setEndDate(dateTo);

        Map<Integer, String> lkaMap = LkaExpert.getLkaMap(regionId, distrId);
        request.setAttribute("lkaMap", lkaMap);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/lkaOptions.jsp");
        dispatcher.forward(request, response);
    }
}
