package com.photochecker.controllers.nka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.model.nka.NkaParam;
import com.photochecker.service.nka.NkaParamService;
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
public class NkaParamController {

    @Autowired
    private NkaParamService nkaParamService;

    @GetMapping("/reports/nka_param")
    public ModelAndView showNkaParams(@Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("nka/nkaParamPage");
        List<NkaParam> paramList = nkaParamService.getAllNkaParams();
        modelAndView.addObject("paramList", paramList);
        modelAndView.addObject("pageTitle", "Параметры сетей");
        modelAndView.addObject("pageCategory", "nkaParam");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }


    @PostMapping(value = "/reports/nka/getParams", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public NkaParam getNkaParams (@RequestParam ("nkaId") int nkaId) {
        return nkaParamService.getNkaParam(nkaId);
    }


    @PostMapping(value = "/reports/nka/saveNewParams", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveNkaParams (@RequestParam ("paramList") String paramListJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<NkaParam>>(){}.getType();
        List<NkaParam> critList = gson.fromJson(paramListJson, type);

        boolean answer = nkaParamService.writeNewNkaParam(critList);
        return Collections.singletonMap("answer", answer);
    }
}
