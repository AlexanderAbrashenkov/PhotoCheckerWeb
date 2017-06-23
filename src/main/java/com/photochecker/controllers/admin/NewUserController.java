package com.photochecker.controllers.admin;

import com.photochecker.model.common.ReportType;
import com.photochecker.model.common.User;
import com.photochecker.service.common.ReportTypeService;
import com.photochecker.service.common.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class NewUserController {

    @Autowired
    private ReportTypeService reportTypeService;
    @Autowired
    private UserService userService;

    @GetMapping("/reports/create_user")
    public ModelAndView showCreateUserPage(@Value("${resVer}") String resVer) {
        ModelAndView modelAndView = new ModelAndView("createUserPage");
        List<ReportType> reportTypes = reportTypeService.getReportTypes();
        modelAndView.addObject("reportTypes", reportTypes);
        modelAndView.addObject("pageTitle", "Новый сотрудник");
        modelAndView.addObject("pageCategory", "administrate");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }

    @PostMapping(value = "/reports/create_user/check_login", params = {"login"}, produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> checkLogin(
            @RequestParam(value = "login") String login) {

        boolean answer = !userService.checkLogin(login);
        return Collections.singletonMap("answer", answer);
    }

    @PostMapping(value = "/reports/create_user/add_user",
            params = {"login", "password", "fio", "role", "report_types"}, produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> addNewUser(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "fio") String userName,
            @RequestParam(value = "role") String role,
            @RequestParam(value = "report_types") String reportTypes) {

        reportTypes = reportTypes.substring(1, reportTypes.length() - 1);
        reportTypes = reportTypes.replace("\"", "");
        String[] reps = reportTypes.split(",");

        List<ReportType> allReportTypeList = reportTypeService.getReportTypes();

        List<ReportType> newUserReports = new ArrayList<>();
        for (int i = 0; i < reps.length; i++) {
            int reportId = Integer.parseInt(reps[i]);
            newUserReports.add(allReportTypeList.stream()
                    .filter(reportType1 -> reportType1.getId() == reportId)
                    .findFirst()
                    .get());
        }

        User user = new User(0, login, userName, Integer.parseInt(role), newUserReports);

        userService.saveNewUser(user, password);

        boolean answer = true;
        return Collections.singletonMap("answer", answer);
    }
}
