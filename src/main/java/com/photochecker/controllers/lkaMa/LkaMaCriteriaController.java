package com.photochecker.controllers.lkaMa;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lkaMa.LkaMaCriterias;
import com.photochecker.service.common.LkaService;
import com.photochecker.service.lkaMa.LkaMaCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObject;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
public class LkaMaCriteriaController {

    @Autowired
    private LkaMaCriteriasService lkaMaCriteriasService;
    @Autowired
    private LkaService lkaService;

    @GetMapping("/reports/lka_ma_criteria")
    public ModelAndView showLkaCriterias(@Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("lkaMa/lkaMaCriteriaPage");
        List<LkaMaCriterias> lkaMaCriterias = lkaMaCriteriasService.getAllLkaCriterias();
        modelAndView.addObject("critList", lkaMaCriterias);
        modelAndView.addObject("pageTitle", "Критерии LKA MA");
        modelAndView.addObject("pageCategory", "administrate");
        modelAndView.addObject("resVer", resVer);

        return modelAndView;
    }


    @PostMapping(value = "/reports/lka_ma/getCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLkaCriterias (@RequestParam ("lkaId") int lkaId) {
        LkaMaCriterias lkaMaCriterias = lkaMaCriteriasService.getLkaCriterias(lkaId);
        if (lkaMaCriterias == null) {
            lkaMaCriterias = new LkaMaCriterias(null, "Доля полки",
                    10, 10, 10, 10, "Бренд-блок");
        }
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("hasMz", lkaMaCriterias.getCrit1Mz() > 0)
                .add("crit1Mz", lkaMaCriterias.getCrit1Name() + ": " + lkaMaCriterias.getCrit1Mz() + "%")
                .add("hasK", lkaMaCriterias.getCrit1K() > 0)
                .add("crit1K", lkaMaCriterias.getCrit1Name() + ": " + lkaMaCriterias.getCrit1K() + "%")
                .add("hasS", lkaMaCriterias.getCrit1S() > 0)
                .add("crit1S", lkaMaCriterias.getCrit1Name() + ": " + lkaMaCriterias.getCrit1S() + "%")
                .add("hasM", lkaMaCriterias.getCrit1M() > 0)
                .add("crit1M", lkaMaCriterias.getCrit1Name() + ": " + lkaMaCriterias.getCrit1M() + "%")
                .add("crit2", lkaMaCriterias.getCrit2Name())
                .build();
        return jsonObject.toString();
    }


    @PostMapping(value = "/reports/lka_ma/saveNewCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveLkaCriterias (@RequestParam ("critList") String critListJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<LkaMaCriterias>>(){}.getType();
        List<LkaMaCriterias> critList = gson.fromJson(critListJson, type);

        boolean answer = lkaMaCriteriasService.writeNewLkaCriterias(critList);
        return Collections.singletonMap("answer", answer);
    }
}
