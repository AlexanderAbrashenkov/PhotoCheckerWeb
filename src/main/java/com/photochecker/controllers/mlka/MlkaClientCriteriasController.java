package com.photochecker.controllers.mlka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.mlka.MlkaClientCriterias;
import com.photochecker.service.mlka.MlkaClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;


@Controller
public class MlkaClientCriteriasController {

    @Autowired
    private MlkaClientCriteriasService mlkaClientCriteriasService;

    @PostMapping(value = "/reports/mlka/saveCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveMlkaClientCriterias(@RequestParam("dateFrom") String dateFromS,
                                                        @RequestParam("dateTo") String dateToS,
                                                        @RequestParam("crit") String critJson) {

        Gson gson = new Gson();
        Type type = new TypeToken<MlkaClientCriterias>(){}.getType();
        MlkaClientCriterias mlkaClientCriterias = gson.fromJson(critJson, type);

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        mlkaClientCriterias.setDateFrom(dateFrom);
        mlkaClientCriterias.setDateTo(dateTo);
        mlkaClientCriterias.setSaveDate(LocalDateTime.now());

        boolean answer = mlkaClientCriteriasService.saveCriterias(mlkaClientCriterias);
        return Collections.singletonMap("answer", answer);
    }


    @PostMapping(value = "/reports/mlka/getSavedCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public MlkaClientCriterias getSavedMlkaClientCriterias(@RequestParam("dateFrom") String dateFromS,
                                                           @RequestParam("dateTo") String dateToS,
                                                           @RequestParam("clientId") int clientId) {

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        return mlkaClientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);
    }
}
