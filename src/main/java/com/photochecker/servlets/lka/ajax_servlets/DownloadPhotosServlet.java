package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.models.ClientCard;
import com.photochecker.models.PhotoCard;
import com.photochecker.models.lka.LkaExpert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by market6 on 28.03.2017.
 */
@WebServlet(
        name = "downloadPhotos",
        urlPatterns = "/reports/lka/getPhotos"
)
public class DownloadPhotosServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int clientId = Integer.parseInt(request.getParameter("clientId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);
        dateTo = dateTo.plusDays(1);

        LkaExpert.setStartDate(dateFrom);
        LkaExpert.setEndDate(dateTo);

        List<PhotoCard> photoCardList = LkaExpert.getPhotoList(clientId);

        request.setAttribute("photoList", photoCardList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/photoPane.jsp");
        dispatcher.forward(request, response);
    }
}
