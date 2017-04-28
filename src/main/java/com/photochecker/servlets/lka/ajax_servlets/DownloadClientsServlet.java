package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.model.ClientCard;
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

/**
 * Created by market6 on 27.03.2017.
 */
@WebServlet(
        name = "downloadClients",
        urlPatterns = "/reports/lka/getClients"
)
public class DownloadClientsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        /*int regionId = Integer.parseInt(request.getParameter("regionId"));*/
        int distrId = Integer.parseInt(request.getParameter("distrId"));
        int lkaId = Integer.parseInt(request.getParameter("lkaId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);
        /*dateTo = dateTo.plusDays(1);

        LkaExpert.setStartDate(dateFrom);
        LkaExpert.setEndDate(dateTo);*/

        /*List<ClientCard> clientCardList = LkaExpert.getClientList(regionId, distrId, lkaId);*/
        List<ClientCard> clientCardList = LkaService.getClientCardList(distrId, lkaId, dateFrom, dateTo);
        request.setAttribute("clientsList", clientCardList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/addressTable.jsp");
        dispatcher.forward(request, response);
    }
}
