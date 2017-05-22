package com.photochecker.servlets.lka.ajax_servlets;

import com.google.gson.Gson;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.service.lka.ClientCriteriasService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by market6 on 30.03.2017.
 */
@WebServlet(
        name = "downLoadSavedCriterias",
        urlPatterns = "/reports/lka/getSavedCriterias")
public class DownLoadSavedCriteriasServlet extends HttpServlet {

    private ClientCriteriasService clientCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        clientCriteriasService = (ClientCriteriasService) context.getBean("clientCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int clientId = Integer.parseInt(request.getParameter("clientId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        ClientCriterias clientCriterias = clientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);

        Gson gson = new Gson();
        String jsonString = gson.toJson(clientCriterias);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }
}
