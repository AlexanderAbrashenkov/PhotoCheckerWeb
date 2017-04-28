package com.photochecker.servlets.lka.ajax_servlets;

import com.photochecker.model.lka.LkaExpert;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by market6 on 07.04.2017.
 */
@WebServlet(name = "LkaNameByIdServlet",
urlPatterns = "/reports/lka/getLkaNameById")
public class LkaNameByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("lkaId"));
        } catch (Exception e) {
            id = -1;
        }
        String lkaName = LkaExpert.getLkaNameById(id);

        JsonObject jsonObject;
        if (lkaName != null) {
            jsonObject = Json.createObjectBuilder()
                    .add("lkaName", lkaName)
                    .build();
        } else {
            jsonObject = Json.createObjectBuilder()
                    .add("lkaName", "")
                    .build();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
