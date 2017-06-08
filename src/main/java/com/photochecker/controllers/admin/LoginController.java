package com.photochecker.controllers.admin;

import com.photochecker.model.common.User;
import com.photochecker.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("error", false);
        modelAndView.addObject("pageTitle", "Авторизация");
        return modelAndView;
    }

    @PostMapping(value = "/login", params = {"login_login", "login_password"})
    public ModelAndView checkLogin(
            HttpSession session,
            @RequestParam(value = "login_login") String login,
            @RequestParam(value = "login_password") String password) {

        ModelAndView modelAndView = new ModelAndView();

        User user = userService.loginUser(login, password);

        if (null == user) {
            modelAndView.addObject("error", true);
            modelAndView.addObject("pageTitle", "Авторизация");
            modelAndView.setViewName("login");
            return modelAndView;
        }

        session.setAttribute("user", user);
        String lastUrl = (String) session.getAttribute("lastUrl");
        String dispatherPath;
        if (null == lastUrl) {
            dispatherPath = "/";
        } else if (lastUrl.startsWith("/reports")) {
            dispatherPath = "/reports";
        } else if (lastUrl.startsWith("/route")) {
            dispatherPath = "/route";
        } else {
            dispatherPath = "/";
        }
        modelAndView.setViewName("redirect:" + dispatherPath);
        return modelAndView;
    }

    @GetMapping("/logoff")
    public ModelAndView logoff(HttpSession session) {
        session.removeAttribute("user");
        return new ModelAndView("redirect:/");
    }
}
