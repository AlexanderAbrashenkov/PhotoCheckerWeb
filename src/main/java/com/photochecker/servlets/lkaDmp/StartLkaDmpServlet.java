package com.photochecker.servlets.lkaDmp;

import com.photochecker.model.common.Region;
import com.photochecker.model.common.User;
import com.photochecker.service.common.CommonService;
import com.photochecker.service.common.RegionService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
        name = "startLkaDmpServlet",
        urlPatterns = "/reports/lkaDmp"
)
public class StartLkaDmpServlet extends HttpServlet {

    private CommonService commonService;
    private RegionService regionService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
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
                startDate, endDate, 1);
        request.setAttribute("regionList", regionList);
        request.setAttribute("pageTitle", "Фотоотчет LKA ДМП");
        request.setAttribute("pageCategory", "lkaDmp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("../WEB-INF/view/lkaDmp/lkaDmpPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
