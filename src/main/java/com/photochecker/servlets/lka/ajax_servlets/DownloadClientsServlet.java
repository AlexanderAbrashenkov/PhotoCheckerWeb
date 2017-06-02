package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.model.common.ClientCard;
import com.photochecker.service.common.ClientCardService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
    private ClientCardService clientCardService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        clientCardService = (ClientCardService) context.getBean("clientCardService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int distrId = Integer.parseInt(request.getParameter("distrId"));
        int lkaId = Integer.parseInt(request.getParameter("lkaId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        List<ClientCard> clientCardList = clientCardService.getClientCardList(distrId, lkaId, 1, dateFrom, dateTo, 5);
        request.setAttribute("clientsList", clientCardList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/addressTable.jsp");
        dispatcher.forward(request, response);
    }
}
