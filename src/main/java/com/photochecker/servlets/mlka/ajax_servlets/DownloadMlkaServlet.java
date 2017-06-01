package com.photochecker.servlets.mlka.ajax_servlets;

import com.photochecker.model.Lka;
import com.photochecker.model.mlka.Employee;
import com.photochecker.service.common.LkaService;
import com.photochecker.service.mlka.EmployeeService;
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
        name = "downloadMlkas",
        urlPatterns = "/reports/mlka/getMlkas"
)
public class DownloadMlkaServlet extends HttpServlet {
    private EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        employeeService = (EmployeeService) context.getBean("employeeService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int nkaId = Integer.parseInt(request.getParameter("nkaId"));
        int distrId = Integer.parseInt(request.getParameter("distrId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

       List<Employee> employeeList = employeeService.getEmployees(distrId, dateFrom, dateTo, 2, nkaId);
        request.setAttribute("mlkaList", employeeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/mlka/ajax_parts/mlkaOptions.jsp");
        dispatcher.forward(request, response);
    }
}
