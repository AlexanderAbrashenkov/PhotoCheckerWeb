package com.photochecker.servlets.mlka.ajax_servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.model.mlka.MlkaClientCriterias;
import com.photochecker.service.lka.ClientCriteriasService;
import com.photochecker.service.mlka.MlkaClientCriteriasService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by market6 on 30.03.2017.
 */
@WebServlet(
        name = "saveMlkaCriterias",
        urlPatterns = "/reports/mlka/saveCriterias"
)
public class SaveCriteriasServlet extends HttpServlet {

    private MlkaClientCriteriasService mlkaClientCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        mlkaClientCriteriasService = (MlkaClientCriteriasService) context.getBean("mlkaClientCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String s = request.getParameter("crit");

        Gson gson = new Gson();
        Type type = new TypeToken<MlkaClientCriterias>(){}.getType();
        MlkaClientCriterias mlkaClientCriterias = gson.fromJson(s, type);

        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        mlkaClientCriterias.setDateFrom(dateFrom);
        mlkaClientCriterias.setDateTo(dateTo);
        mlkaClientCriterias.setSaveDate(LocalDateTime.now());

        boolean isSaveSucceed = mlkaClientCriteriasService.saveCriterias(mlkaClientCriterias);
        response.getWriter().write(String.valueOf(isSaveSucceed));
    }
}
