package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.model.Lka;
import com.photochecker.service.lka.LkaService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        name = "downloadLkas",
        urlPatterns = "/reports/lka/getLkas"
)
public class DownloadLkaServlet extends HttpServlet {
    LkaService lkaService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        lkaService = (LkaService) context.getBean("lkaService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int distrId = Integer.parseInt(request.getParameter("distrId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

       List<Lka> lkaList = lkaService.getLkas(distrId, dateFrom, dateTo);
        request.setAttribute("lkaList", lkaList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/lkaOptions.jsp");
        dispatcher.forward(request, response);
    }
}
