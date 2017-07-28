package com.photochecker.controllers.nka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.nka.NkaClientCriterias;
import com.photochecker.service.nka.NkaClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;


@Controller
public class NkaClientCriteriasController {

    @Autowired
    private NkaClientCriteriasService nkaClientCriteriasService;

    @PostMapping(value = "/reports/nka/saveCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveNkaClientCriterias(@RequestParam("dateFrom") String dateFromS,
                                                        @RequestParam("dateTo") String dateToS,
                                                        @RequestParam("photoUrls") String photoUrls,
                                                        @RequestParam("crit") String critJson) {

        Gson gson = new Gson();
        Type type = new TypeToken<NkaClientCriterias>(){}.getType();
        NkaClientCriterias nkaClientCriterias = gson.fromJson(critJson, type);

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        nkaClientCriterias.setDateFrom(dateFrom);
        nkaClientCriterias.setDateTo(dateTo);
        nkaClientCriterias.setSaveDate(LocalDateTime.now());

        photoUrls = photoUrls.replace("[", "")
                .replace("]", "")
                .replace("\"", "");
        ArrayList<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

        boolean answer = nkaClientCriteriasService.saveCriterias(nkaClientCriterias, photoUrlList);
        return Collections.singletonMap("answer", answer);
    }


    @PostMapping(value = "/reports/nka/getSavedCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public NkaClientCriterias getSavedNkaClientCriterias(@RequestParam("dateFrom") String dateFromS,
                                                           @RequestParam("dateTo") String dateToS,
                                                           @RequestParam("clientId") int clientId) {

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        return nkaClientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);
    }
}
