package com.photochecker.servlets.lkaDmp.ajax_servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.service.lka.ClientCriteriasService;
import com.photochecker.service.lkaDmp.DmpClientCriteriasService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by market6 on 30.03.2017.
 */
@WebServlet(
        name = "saveLkaDmpCriterias",
        urlPatterns = "/reports/lkaDmp/saveCriterias"
)
public class SaveCriteriasServlet extends HttpServlet {

    private DmpClientCriteriasService dmpClientCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        dmpClientCriteriasService = (DmpClientCriteriasService) context.getBean("dmpClientCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getParameter("dmpArray");

        List<DmpClientCriterias> critList = new ArrayList<>();

        Gson gson = new Gson();
        Type type = new TypeToken<Collection<DmpClientCriterias>>(){}.getType();
        critList = gson.fromJson(s, type);

        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        for (DmpClientCriterias dmpClientCriterias : critList) {
            dmpClientCriterias.setDateFrom(dateFrom);
            dmpClientCriterias.setDateTo(dateTo);
            dmpClientCriterias.setSaveDate(LocalDateTime.now());
        }

        boolean isSaveSucceed = dmpClientCriteriasService.saveCriterias(critList);
        response.getWriter().write(String.valueOf(isSaveSucceed));
    }
}
