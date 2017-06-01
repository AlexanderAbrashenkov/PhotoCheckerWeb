package com.photochecker.servlets.mlka.ajax_servlets;

import com.google.gson.Gson;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.model.mlka.MlkaClientCriterias;
import com.photochecker.service.lkaDmp.DmpClientCriteriasService;
import com.photochecker.service.mlka.MlkaClientCriteriasService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(
        name = "downLoadMlkaSavedCriterias",
        urlPatterns = "/reports/mlka/getSavedCriterias")
public class DownloadSavedCriteriasServlet extends HttpServlet {

    private MlkaClientCriteriasService mlkaClientCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        mlkaClientCriteriasService = (MlkaClientCriteriasService) context.getBean("mlkaClientCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int clientId = Integer.parseInt(request.getParameter("clientId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        MlkaClientCriterias clientCriterias = mlkaClientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);

        Gson gson = new Gson();
        String jsonString = gson.toJson(clientCriterias);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }
}