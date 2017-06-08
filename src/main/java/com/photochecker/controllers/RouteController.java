package com.photochecker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RouteController {

    @GetMapping("/route")
    public ModelAndView showRouteMenu() {
        return new ModelAndView("route");
    }
}
