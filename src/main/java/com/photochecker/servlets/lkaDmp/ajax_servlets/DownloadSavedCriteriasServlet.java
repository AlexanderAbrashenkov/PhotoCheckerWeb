package com.photochecker.servlets.lkaDmp.ajax_servlets;

import com.google.gson.Gson;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.service.lkaDmp.DmpClientCriteriasService;
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
        name = "downLoadLkaDmpSavedCriterias",
        urlPatterns = "/reports/lkaDmp/getSavedCriterias")
public class DownloadSavedCriteriasServlet extends HttpServlet {

    private DmpClientCriteriasService dmpClientCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        dmpClientCriteriasService = (DmpClientCriteriasService) context.getBean("dmpClientCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int clientId = Integer.parseInt(request.getParameter("clientId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        List<DmpClientCriterias> dmpClientCriterias = dmpClientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);

        Gson gson = new Gson();
        String jsonString = gson.toJson(dmpClientCriterias);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
    }
}