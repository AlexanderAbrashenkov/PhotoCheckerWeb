package com.photochecker.controllers.dmp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.model.lkaDmp.DmpClientCriterias;
import com.photochecker.service.lkaDmp.DmpClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Controller
public class DmpClientCriteriasController {

    @Autowired
    private DmpClientCriteriasService dmpClientCriteriasService;

    @PostMapping(value = "/reports/lkaDmp/saveCriterias", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> saveDmpClientCriterias(@RequestParam("dmpArray") String dmpArrayJson,
                                                       @RequestParam("photoUrls") String photoUrls,
                                                       @RequestParam("dateFrom") String dateFromS,
                                                       @RequestParam("dateTo") String dateToS) {

        List<DmpClientCriterias> critList = new ArrayList<>();

        Gson gson = new Gson();
        Type type = new TypeToken<Collection<DmpClientCriterias>>(){}.getType();
        critList = gson.fromJson(dmpArrayJson, type);

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        for (DmpClientCriterias dmpClientCriterias : critList) {
            dmpClientCriterias.setDateFrom(dateFrom);
            dmpClientCriterias.setDateTo(dateTo);
            dmpClientCriterias.setSaveDate(LocalDateTime.now());
        }

        photoUrls = photoUrls.replace("[", "")
                .replace("]", "")
                .replace("\"", "");
        ArrayList<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));
        boolean answer = dmpClientCriteriasService.saveCriterias(critList, photoUrlList);

        return Collections.singletonMap("answer", answer);
    }


    @PostMapping(value = "/reports/lkaDmp/getSavedCriterias", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<DmpClientCriterias> getSavedDmpClientCriterias(@RequestParam("dateFrom") String dateFromS,
                                                         @RequestParam("dateTo") String dateToS,
                                                         @RequestParam("clientId") int clientId) {

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        return dmpClientCriteriasService.getSavedCriterias(clientId, dateFrom, dateTo);
    }
}


