package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.model.Region;
import com.photochecker.model.User;
import com.photochecker.service.common.RegionService;
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
        name = "downloadRegions",
        urlPatterns = "/reports/lka/getRegions"
)
public class DownloadRegionsServlet extends HttpServlet {
    private RegionService regionService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        regionService = (RegionService) context.getBean("regionService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        User user = (User) request.getSession().getAttribute("user");
        List<Region> regionList = regionService.getRegions(user, dateFrom, dateTo, 5);
        request.setAttribute("regionList", regionList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lka/ajax_parts/regionOptions.jsp");
        dispatcher.forward(request, response);
    }
}
