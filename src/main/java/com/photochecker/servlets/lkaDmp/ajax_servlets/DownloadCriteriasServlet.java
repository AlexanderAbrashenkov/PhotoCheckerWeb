package com.photochecker.servlets.lkaDmp.ajax_servlets;

import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.service.lka.LkaCriteriasService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        name = "downloadLkaDmpCriterias",
        urlPatterns = "/reports/lkaDmp/getCriterias"
)
public class DownloadCriteriasServlet extends HttpServlet {
    private LkaCriteriasService lkaCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        lkaCriteriasService = (LkaCriteriasService) context.getBean("lkaCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lkaId = Integer.parseInt(request.getParameter("lkaId"));
        LkaCriterias lkaCriterias = lkaCriteriasService.getLkaCriterias(lkaId);
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
