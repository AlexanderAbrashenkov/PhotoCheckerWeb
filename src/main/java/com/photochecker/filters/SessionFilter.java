package com.photochecker.filters;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by market6 on 30.03.2017.
 */

@WebFilter(filterName = "SessionFilter",
    urlPatterns = {"/reports", "/reports/*",
                    "/route", "/route/*",
                    "/css/*", "/js/*", "/images/*"})
public class SessionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String lastUrl = request.getRequestURI();
        request.getSession().setAttribute("lastUrl", lastUrl);
        if (null == request.getSession(false) || null == request.getSession().getAttribute("user")) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                JsonObject jsonObject = Json.createObjectBuilder()
                        .add("urlToRedirect", "login")
                        .build();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonObject.toString());
                return;
            } else {
                ((HttpServletResponse) resp).sendRedirect("/login");
                return;
            }
        }

        if (lastUrl.startsWith("/reports/lka")) {
            req.setAttribute("addRepLink", true);
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
