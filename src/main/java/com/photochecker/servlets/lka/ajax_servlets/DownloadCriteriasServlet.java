package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.model.lka.LkaExpert;
import com.photochecker.service.LkaService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by market6 on 29.03.2017.
 */
@WebServlet(
        name = "downloadCriterias",
        urlPatterns = "/reports/lka/getCriterias"
)
public class DownloadCriteriasServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lkaId = Integer.parseInt(request.getParameter("lkaId"));
        /*LkaCriterias lkaCriterias = LkaExpert.getLkaCriterias(lkaId);*/
        LkaCriterias lkaCriterias = LkaService.getLkaCriterias(lkaId);
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("hasMz", lkaCriterias.getCrit1Mz() > 0)
                .add("crit1Mz", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1Mz() + "%")
                .add("hasK", lkaCriterias.getCrit1K() > 0)
                .add("crit1K", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1K() + "%")
                .add("hasS", lkaCriterias.getCrit1S() > 0)
                .add("crit1S", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1S() + "%")
                .add("hasM", lkaCriterias.getCrit1M() > 0)
                .add("crit1M", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1M() + "%")
                .add("crit2", lkaCriterias.getCrit2Name())
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
