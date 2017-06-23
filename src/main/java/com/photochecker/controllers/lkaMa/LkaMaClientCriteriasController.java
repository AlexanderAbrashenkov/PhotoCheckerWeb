package com.photochecker.controllers.lkaMa;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lkaMa.LkaMaClientCriterias;
import com.photochecker.service.lkaMa.LkaMaClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;


@Controller
public class LkaMaClientCriteriasController {

    @Autowired
    private LkaMaClientCriteriasService lkaMaClientCriteriasService;


    @PostMapping(value = "/reports/lka_ma/getSavedCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public LkaMaClientCriterias getClientCriterias (@RequestParam("dateFrom") String dateFromS,
                                                    @RequestParam("dateTo") String dateToS,
                                                    @RequestParam("clientId") int clientId) {

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);
        return lkaMaClientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);
    }


    @PostMapping(value = "/reports/lka_ma/saveCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveClientCriterias (@RequestParam("clientCrit") String lkaMaClientCriteriasJson,
                                                     @RequestParam("dateFrom") String dateFromS,
                                                     @RequestParam("dateTo") String dateToS) {

        Gson gson = new Gson();
        Type type = new TypeToken<LkaMaClientCriterias>(){}.getType();
        LkaMaClientCriterias lkaMaClientCriterias = gson.fromJson(lkaMaClientCriteriasJson, type);

        System.out.println(lkaMaClientCriterias);
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);
        lkaMaClientCriterias.setDateFrom(dateFrom);
        lkaMaClientCriterias.setDateTo(dateTo);
        lkaMaClientCriterias.setSaveDate(LocalDateTime.now());
        boolean answer = lkaMaClientCriteriasService.saveCriterias(lkaMaClientCriterias);
        return Collections.singletonMap("answer", answer);
    }
}
