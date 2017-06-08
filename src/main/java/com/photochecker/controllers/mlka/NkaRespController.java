package com.photochecker.controllers.mlka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.common.User;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.service.common.UserService;
import com.photochecker.service.mlka.NkaRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
public class NkaRespController {

    @Autowired
    NkaRespService nkaRespService;
    @Autowired
    UserService userService;

    @GetMapping("/reports/mlkaResp")
    public ModelAndView getNkaRespPage() {
        ModelAndView modelAndView = new ModelAndView("mlka/nkaRespPage");

        List<NkaResp> nkaRespList = nkaRespService.getAllNkaResp();
        List<User> respUsers = userService.getRespUsers().get(new Integer(2));
        modelAndView.addObject("respList", nkaRespList);
        modelAndView.addObject("respUsers", respUsers);
        modelAndView.addObject("pageTitle", "Ответственные");
        modelAndView.addObject("pageCategory", "administrate");
        return modelAndView;
    }


    @PostMapping(value = "/reports/mlkaResp/save", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveNkaResp (@RequestParam("nkaRespList") String nkaRespListJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<NkaResp>>(){}.getType();
        List<NkaResp> nkaRespList = gson.fromJson(nkaRespListJson, type);

        boolean succeed = nkaRespService.writeNkaResp(nkaRespList);
        return Collections.singletonMap("answer", succeed);
    }
}
