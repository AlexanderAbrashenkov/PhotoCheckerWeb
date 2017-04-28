package com.photochecker.servlets.admin;

import com.photochecker.model.User;
import com.photochecker.model.lka.LkaExpert;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by market6 on 26.04.2017.
 */
@WebServlet(name = "AddNewUserServlet",
        urlPatterns = "/reports/create_user/add_user")
public class AddNewUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String userName = request.getParameter("fio");
        String role = request.getParameter("role");
        String reportTypes = request.getParameter("report_types");
        reportTypes = reportTypes.substring(1, reportTypes.length() - 1);
        reportTypes = reportTypes.replace("\"", "");
        String[] reps = reportTypes.split(",");
        Integer[] repsInt = new Integer[reps.length];

        for (int i = 0; i < reps.length; i++) {
            repsInt[i] = Integer.parseInt(reps[i]);
        }

        User user = new User(0, login, userName, Integer.parseInt(role), Arrays.asList(repsInt));

        boolean success = LkaExpert.saveNewUser(user, pass);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("answer", success)
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}