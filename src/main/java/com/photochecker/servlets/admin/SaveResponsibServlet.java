package com.photochecker.servlets.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.Responsibility;
import com.photochecker.service.common.ResponsibilitiesService;
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
 * Created by market6 on 21.04.2017.
 */
@WebServlet(name = "SaveResponsibServlet",
urlPatterns = "/reports/responsib/save")
public class SaveResponsibServlet extends HttpServlet {

    private ResponsibilitiesService responsibilitiesService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        responsibilitiesService = (ResponsibilitiesService) context.getBean("responsibilitiesService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Responsibility>>(){}.getType();

        List<Responsibility> respList = gson.fromJson(request.getParameter("respList"), type);

        boolean succeed = responsibilitiesService.writeResponsibilities(respList);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("answer", succeed)
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
