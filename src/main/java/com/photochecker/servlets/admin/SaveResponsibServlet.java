package com.photochecker.servlets.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.Responsibility;
import com.photochecker.service.MainService;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Responsibility>>(){}.getType();

        List<Responsibility> respList = gson.fromJson(request.getParameter("respList"), type);

        boolean succeed = MainService.writeResponsibilities(respList);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("answer", succeed)
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
