package com.photochecker.controllers.nst;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstResp;
import com.photochecker.service.common.UserService;
import com.photochecker.service.nst.NstRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class NstRespController {

    @Autowired
    NstRespService nstRespService;
    @Autowired
    UserService userService;

    @GetMapping("/reports/nstResp")
    public ModelAndView getNstRespPage(@Value("${resVer}") String resVer) {
        ModelAndView modelAndView = new ModelAndView("nst/nstRespPage");

        List<NstResp> nstRespList = nstRespService.getAllNstResp();
        List<User> respUsers = userService.getRespUsers().get("4");
        modelAndView.addObject("respList", nstRespList);
        modelAndView.addObject("respUsers", respUsers);
        modelAndView.addObject("pageTitle", "Ответственные");
        modelAndView.addObject("pageCategory", "administrate");
        modelAndView.addObject("resVer", resVer);

        return modelAndView;
    }


    @PostMapping(value = "/reports/nstResp/save", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveNstResp (@RequestParam("nstRespList") String nstRespListJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<NstResp>>(){}.getType();
        List<NstResp> nstRespList = gson.fromJson(nstRespListJson, type);

        boolean succeed = nstRespService.writeNstResp(nstRespList);
        return Collections.singletonMap("answer", succeed);
    }
}
