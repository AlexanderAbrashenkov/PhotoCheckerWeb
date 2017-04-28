package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.model.Distr;
import com.photochecker.model.User;
import com.photochecker.model.lka.LkaExpert;
import com.photochecker.service.LkaService;

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
        //dateTo = dateTo.plusDays(1);

        /*LkaExpert.setStartDate(dateFrom);
        LkaExpert.setEndDate(dateTo);
        Map<Integer, String> distrMap = LkaExpert.getDistrMap(regionId, (User) request.getSession().getAttribute("user"));*/
        User user = (User) request.getSession().getAttribute("user");
        List<Distr> distrList = LkaService.getDistrs(user, regionId, dateFrom, dateTo);
        request.setAttribute("distrList", distrList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/distrOptions.jsp");
        dispatcher.forward(request, response);
    }
}
