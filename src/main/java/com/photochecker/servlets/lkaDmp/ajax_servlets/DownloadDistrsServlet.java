package com.photochecker.servlets.lkaDmp.ajax_servlets;

import com.photochecker.model.Distr;
import com.photochecker.model.User;
import com.photochecker.service.common.DistrService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
        name = "downloadLkaDmpDistrs",
        urlPatterns = "/reports/lkaDmp/getDistrs"
)
public class DownloadDistrsServlet extends HttpServlet {
    private DistrService distrService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        distrService = (DistrService) context.getBean("distrService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int regionId = Integer.parseInt(request.getParameter("regionId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        User user = (User) request.getSession().getAttribute("user");
        List<Distr> distrList = distrService.getDistrs(user, regionId, dateFrom, dateTo, 1);
        request.setAttribute("distrList", distrList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lkaDmp/ajax_parts/distrOptions.jsp");
        dispatcher.forward(request, response);
    }
}