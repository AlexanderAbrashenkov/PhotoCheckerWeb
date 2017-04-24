package com.photochecker.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by market6 on 24.04.2017.
 */
@WebServlet(name = "RouteServlet",
        urlPatterns = "/route")
public class RouteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/view/route.jsp");
        requestDispatcher.forward(request, response);

        Collections.sort(new ArrayList<Integer>());
    }
}
