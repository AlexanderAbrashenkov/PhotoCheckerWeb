package com.photochecker.controllers.lka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.service.lka.ClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
public class ClientCriteriasController {

    @Autowired
    private ClientCriteriasService clientCriteriasService;


    @PostMapping(value = "/reports/lka/getSavedCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ClientCriterias getClientCriterias (@RequestParam("dateFrom") String dateFromS,
                                                             @RequestParam("dateTo") String dateToS,
                                                             @RequestParam("clientId") int clientId) {

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);
        return clientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);
    }


    @PostMapping(value = "/reports/lka/saveCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveClientCriterias (@RequestParam("clientCrit") String clientCriteriasJson,
                                                     @RequestParam("dateFrom") String dateFromS,
                                                     @RequestParam("dateTo") String dateToS) {

        Gson gson = new Gson();
        Type type = new TypeToken<ClientCriterias>(){}.getType();
        ClientCriterias clientCriterias = gson.fromJson(clientCriteriasJson, type);

        System.out.println(clientCriterias);
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);
        clientCriterias.setDateFrom(dateFrom);
        clientCriterias.setDateTo(dateTo);
        clientCriterias.setSaveDate(LocalDateTime.now());
        boolean answer = clientCriteriasService.saveCriterias(clientCriterias);
        return Collections.singletonMap("answer", answer);
    }
}
