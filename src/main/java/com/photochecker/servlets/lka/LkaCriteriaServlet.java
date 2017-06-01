package com.photochecker.servlets.lka;

import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.service.lka.LkaCriteriasService;
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
import java.util.List;

/**
 * Created by market6 on 06.04.2017.
 */
@WebServlet(name = "LkaCriteriaServlet",
urlPatterns = "/reports/lka_criteria")
public class LkaCriteriaServlet extends HttpServlet {

    private LkaCriteriasService lkaCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        lkaCriteriasService = (LkaCriteriasService) context.getBean("lkaCriteriasService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<LkaCriterias> criteriasList = lkaCriteriasService.getAllLkaCriterias();
        request.setAttribute("critList", criteriasList);
        request.setAttribute("pageTitle", "Критерии LKA");
        request.setAttribute("pageCategory", "administrate");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/lka/lkaCriteriaPage.jsp");
        dispatcher.forward(request, response);
    }
}
