package com.photochecker.servlets.mlka.ajax_servlets;

import com.photochecker.model.Region;
import com.photochecker.model.User;
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
 * Created by market6 on 27.03.2017.
 */
@WebServlet(
        name = "downloadMlkaRegions",
        urlPatterns = "/reports/mlka/getRegions"
)
public class DownloadRegionsServlet extends HttpServlet {
    private RegionService regionService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        regionService = (RegionService) context.getBean("regionService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int nkaId = Integer.parseInt(request.getParameter("nkaId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        User user = (User) request.getSession().getAttribute("user");
        List<Region> regionList = regionService.getRegions(user, dateFrom, dateTo, 2, nkaId);
        request.setAttribute("regionList", regionList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/mlka/ajax_parts/regionOptions.jsp");
        dispatcher.forward(request, response);
    }
}