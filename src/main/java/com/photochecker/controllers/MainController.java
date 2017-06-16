package com.photochecker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView showIndexPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/error")
    public ModelAndView showErrorPage() {
        return new ModelAndView("error");
    }
}
