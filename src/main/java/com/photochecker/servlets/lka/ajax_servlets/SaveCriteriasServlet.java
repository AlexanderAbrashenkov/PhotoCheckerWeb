package com.photochecker.servlets.lka.ajax_servlets;

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
import java.time.LocalDateTime;

/**
 * Created by market6 on 30.03.2017.
 */
@WebServlet(
        name = "saveCriterias",
        urlPatterns = "/reports/lka/saveCriterias"
)
public class SaveCriteriasServlet extends HttpServlet {

    private ClientCriteriasService clientCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        clientCriteriasService = (ClientCriteriasService) context.getBean("clientCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClientCriterias clientCriterias = new ClientCriterias(
                Integer.parseInt(request.getParameter("clientId")),
                LocalDate.parse(request.getParameter("dateFrom")),
                LocalDate.parse(request.getParameter("dateTo")),
                LocalDateTime.now(),

                Boolean.parseBoolean(request.getParameter("hasMz")),
                Boolean.parseBoolean(request.getParameter("hasPhotoMz")),
                Boolean.parseBoolean(request.getParameter("isCorrectMz")),
                Boolean.parseBoolean(request.getParameter("hasAddProd")),
                Boolean.parseBoolean(request.getParameter("crit1Mz")),
                Boolean.parseBoolean(request.getParameter("crit2Mz")),

                Boolean.parseBoolean(request.getParameter("hasK")),
                Boolean.parseBoolean(request.getParameter("hasPhotoK")),
                Boolean.parseBoolean(request.getParameter("isCorrectK")),
                Boolean.parseBoolean(request.getParameter("crit1K")),
                Boolean.parseBoolean(request.getParameter("crit2K")),

                Boolean.parseBoolean(request.getParameter("hasS")),
                Boolean.parseBoolean(request.getParameter("hasPhotoS")),
                Boolean.parseBoolean(request.getParameter("isCorrectS")),
                Boolean.parseBoolean(request.getParameter("crit1S")),
                Boolean.parseBoolean(request.getParameter("crit2S")),

                Boolean.parseBoolean(request.getParameter("hasM")),
                Boolean.parseBoolean(request.getParameter("hasPhotoM")),
                Boolean.parseBoolean(request.getParameter("isCorrectM")),
                Boolean.parseBoolean(request.getParameter("crit1M")),
                Boolean.parseBoolean(request.getParameter("crit2M")),

                Boolean.parseBoolean(request.getParameter("oos")),
                request.getParameter("comm")
        );

        boolean isSaveSucceed = clientCriteriasService.saveCriterias(clientCriterias);
        response.getWriter().write(String.valueOf(isSaveSucceed));
    }
}
