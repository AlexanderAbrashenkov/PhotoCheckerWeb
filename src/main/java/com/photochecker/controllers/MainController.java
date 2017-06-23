package com.photochecker.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView showIndexPage(@Value("${resVer}") String resVer) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView showErrorPage(@Value("${resVer}") String resVer) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }
}
