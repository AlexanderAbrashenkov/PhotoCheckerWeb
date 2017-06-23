package com.photochecker.controllers.lka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.service.common.LkaService;
import com.photochecker.service.lka.LkaCriteriasService;
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
public class LkaCriteriaController {

    @Autowired
    private LkaCriteriasService lkaCriteriasService;
    @Autowired
    private LkaService lkaService;

    @GetMapping("/reports/lka_criteria")
    public ModelAndView showLkaCriterias(@Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("lka/lkaCriteriaPage");
        List<LkaCriterias> criteriasList = lkaCriteriasService.getAllLkaCriterias();
        modelAndView.addObject("critList", criteriasList);
        modelAndView.addObject("pageTitle", "Критерии LKA");
        modelAndView.addObject("pageCategory", "administrate");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }


    @PostMapping(value = "/reports/lka/getCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getLkaCriterias (@RequestParam ("lkaId") int lkaId) {
        LkaCriterias lkaCriterias = lkaCriteriasService.getLkaCriterias(lkaId);
        if (lkaCriterias == null) {
            lkaCriterias = new LkaCriterias(null, "Доля полки",
                    10, 10, 10, 10, "Бренд-блок");
        }
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("hasMz", lkaCriterias.getCrit1Mz() > 0)
                .add("crit1Mz", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1Mz() + "%")
                .add("hasK", lkaCriterias.getCrit1K() > 0)
                .add("crit1K", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1K() + "%")
                .add("hasS", lkaCriterias.getCrit1S() > 0)
                .add("crit1S", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1S() + "%")
                .add("hasM", lkaCriterias.getCrit1M() > 0)
                .add("crit1M", lkaCriterias.getCrit1Name() + ": " + lkaCriterias.getCrit1M() + "%")
                .add("crit2", lkaCriterias.getCrit2Name())
                .build();
        return jsonObject.toString();
    }


    @PostMapping(value = "/reports/lka/saveNewCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveLkaCriterias (@RequestParam ("critList") String critListJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<LkaCriterias>>(){}.getType();
        List<LkaCriterias> critList = gson.fromJson(critListJson, type);

        boolean answer = lkaCriteriasService.writeNewLkaCriterias(critList);
        return Collections.singletonMap("answer", answer);
    }
}
