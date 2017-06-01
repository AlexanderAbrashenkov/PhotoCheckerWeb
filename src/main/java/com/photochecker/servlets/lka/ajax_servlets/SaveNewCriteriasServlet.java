package com.photochecker.servlets.lka.ajax_servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.service.lka.LkaCriteriasService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by market6 on 07.04.2017.
 */
@WebServlet(name = "SaveNewCriteriasServlet",
urlPatterns = "/reports/lka/saveNewCriterias")
public class SaveNewCriteriasServlet extends HttpServlet {

    private LkaCriteriasService lkaCriteriasService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        lkaCriteriasService = (LkaCriteriasService) context.getBean("lkaCriteriasService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<List<LkaCriterias>>(){}.getType();
        List<LkaCriterias> critList = gson.fromJson(request.getParameter("critList"), type);

        boolean succeed = lkaCriteriasService.writeNewLkaCriterias(critList);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("answer", succeed)
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
