package com.photochecker.servlets.admin;

import com.photochecker.service.MainService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by market6 on 26.04.2017.
 */
@WebServlet(name = "CheckLoginServlet",
        urlPatterns = "/reports/create_user/check_login")
public class CheckLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");

        boolean isLoginFree = !MainService.checkLogin(login);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("answer", isLoginFree)
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
