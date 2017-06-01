package com.photochecker.filters;

import com.photochecker.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by market6 on 13.04.2017.
 */
@WebFilter(filterName = "RoleFilter", urlPatterns = {"/reports/upload", "/reports/lka_criteria",
        "/reports/create_user", "/reports/responsib"})

public class RoleFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        User user = (User) ((HttpServletRequest) req).getSession().getAttribute("user");
        if (user.getRole() == 1) {
            ((HttpServletResponse) resp).sendError(403);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
