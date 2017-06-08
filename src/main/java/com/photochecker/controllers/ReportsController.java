package com.photochecker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportsController {

    @GetMapping("/reports")
    public ModelAndView showReportMenu() {
        ModelAndView modelAndView = new ModelAndView("reportsPage");
        modelAndView.addObject("pageTitle", "Разделы");
        modelAndView.addObject("pageCategory", "reports");
        return modelAndView;
    }
}
