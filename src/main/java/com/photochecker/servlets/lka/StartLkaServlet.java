package com.photochecker.servlets.lka;

import com.photochecker.model.Region;
import com.photochecker.model.User;
import com.photochecker.service.common.CommonService;
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
 * Created by market6 on 21.03.2017.
 */
@WebServlet(
        name = "startLkaServlet",
        urlPatterns = "/reports/lka"
)
public class StartLkaServlet extends HttpServlet {

    private CommonService commonService;
    private RegionService regionService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        commonService = (CommonService) context.getBean("commonService");
        regionService = (RegionService) context.getBean("regionService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate startDate = commonService.getInitialStartDate();
        LocalDate endDate = commonService.getInitialEndDate();
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        List<Region> regionList = regionService.getRegions((User) request.getSession().getAttribute("user"),
                startDate, endDate, 5);
        request.setAttribute("regionList", regionList);
        request.setAttribute("pageTitle", "Фотоотчет LKA");
        request.setAttribute("pageCategory", "lka");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("../WEB-INF/view/lka/lkaPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
