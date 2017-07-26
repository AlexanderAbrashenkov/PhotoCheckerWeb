package com.photochecker.controllers.nka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.common.FormatType;
import com.photochecker.model.nka.NkaParam;
import com.photochecker.model.nka.NkaTma;
import com.photochecker.service.common.FormatTypeService;
import com.photochecker.service.nka.NkaTmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
public class NkaTmaController {

    @Autowired
    private NkaTmaService nkaTmaService;
    @Autowired
    private FormatTypeService formatTypeService;

    @GetMapping("/reports/nka_tma_param")
    public ModelAndView showNkaTmaList(@Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("nka/nkaTmaPage");
        Map<Integer, List<NkaTma>> nkaTmaMap = nkaTmaService.getAllNkaTmaMap();
        List<FormatType> formatTypeList = formatTypeService.getAllFormats();
        modelAndView.addObject("nkaTmaMap", nkaTmaMap);
        modelAndView.addObject("formatTypeList", formatTypeList);
        modelAndView.addObject("pageTitle", "Акции сетей");
        modelAndView.addObject("pageCategory", "nkaParam");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }


    @PostMapping(value = "/reports/nka/getTmaList", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<NkaTma> getNkaTmaList (@RequestParam ("nkaId") int nkaId,
                                  @RequestParam ("formatId") int formatId,
                                  @RequestParam ("startDate") String startDateS,
                                  @RequestParam ("endDate") String endDateS) {
        LocalDate startDate = LocalDate.parse(startDateS);
        LocalDate endDate = LocalDate.parse(endDateS);
        return nkaTmaService.getNkaTmaByDates(nkaId, startDate, endDate, formatId);
    }


    @PostMapping(value = "/reports/saveTmaParams", produces = "application/json")
    @ResponseBody
    public Map<String, Integer> saveNkaParams (@RequestParam ("paramList") String nkaTmaListJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<NkaTma>>(){}.getType();
        List<NkaTma> nkaTmaList = gson.fromJson(nkaTmaListJson, type);

        int answer = nkaTmaService.writeNewNkaTma(nkaTmaList);
        return Collections.singletonMap("answer", answer);
    }
}
