package com.photochecker.servlets.mlka.ajax_servlets;

import com.photochecker.model.Region;
import com.photochecker.model.User;
import com.photochecker.model.mlka.NkaType;
import com.photochecker.service.common.RegionService;
import com.photochecker.service.mlka.NkaTypeService;
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
        name = "downloadNka",
        urlPatterns = "/reports/mlka/getNka"
)
public class DownloadNkaServlet extends HttpServlet {
    private NkaTypeService nkaTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        nkaTypeService = (NkaTypeService) context.getBean("nkaTypeService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        User user = (User) request.getSession().getAttribute("user");
        List<NkaType> nkaTypeList = nkaTypeService.getNkaTypes(user, dateFrom, dateTo, 2);
        request.setAttribute("nkaTypeList", nkaTypeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/mlka/ajax_parts/nkaOptions.jsp");
        dispatcher.forward(request, response);
    }
}
