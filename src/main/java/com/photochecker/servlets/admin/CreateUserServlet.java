package com.photochecker.servlets.admin;

import com.photochecker.model.ReportType;
import com.photochecker.service.common.ReportTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by market6 on 25.04.2017.
 */
@WebServlet(name = "CreateUserServlet",
        urlPatterns = "/reports/create_user")
public class CreateUserServlet extends HttpServlet {

    private ReportTypeService reportTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        reportTypeService = (ReportTypeService) context.getBean("reportTypeService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ReportType> reportTypes = reportTypeService.getReportTypes();
        System.out.println(reportTypes);
        request.setAttribute("reportTypes", reportTypes);
        request.setAttribute("pageTitle", "Новый сотрудник");
        request.setAttribute("pageCategory", "administrate");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/createUserPage.jsp");
        dispatcher.forward(request, response);
    }
}
