package com.photochecker.controllers.nst;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.mlka.MlkaClientCriterias;
import com.photochecker.model.nst.NstClientCriterias;
import com.photochecker.service.nst.NstClientCriteriasService;
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
public class NstClientCriteriasController {

    @Autowired
    private NstClientCriteriasService nstClientCriteriasService;

    @PostMapping(value = "/reports/nst/saveCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Integer> saveNstClientCriterias(@RequestParam("dateFrom") String dateFromS,
                                                        @RequestParam("dateTo") String dateToS,
                                                        @RequestParam("formatId") int formatId,
                                                        @RequestParam("oblId") int oblId,
                                                        @RequestParam("crit") String critJson) {

        Gson gson = new Gson();
        Type type = new TypeToken<NstClientCriterias>(){}.getType();
        NstClientCriterias nstClientCriterias = gson.fromJson(critJson, type);

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        nstClientCriterias.setDateFrom(dateFrom);
        nstClientCriterias.setDateTo(dateTo);
        nstClientCriterias.setSaveDate(LocalDateTime.now());

        int answer = nstClientCriteriasService.saveCriterias(nstClientCriterias, formatId, oblId);
        return Collections.singletonMap("answer", answer);
    }


    @PostMapping(value = "/reports/nst/getSavedCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public NstClientCriterias getSavedNstClientCriterias(@RequestParam("dateFrom") String dateFromS,
                                                           @RequestParam("dateTo") String dateToS,
                                                           @RequestParam("clientId") int clientId) {

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        return nstClientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);
    }
}
