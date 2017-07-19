package com.photochecker.config;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by market6 on 23.06.2017.
 */
public class WebInit implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("resVer", "1.3");
    }
}
