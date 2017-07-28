package com.photochecker.controllers.nka;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.common.FormatType;
import com.photochecker.model.nka.NkaTma;
import com.photochecker.service.common.FormatTypeService;
import com.photochecker.service.common.LkaService;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Controller
public class NkaTmaController {

    @Autowired
    private NkaTmaService nkaTmaService;
    @Autowired
    private FormatTypeService formatTypeService;
    @Autowired
    private LkaService lkaService;

    @GetMapping("/reports/nka_tma_param")
    public ModelAndView showNkaTmaList(@Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("nka/nkaTmaPage");
        Map<String, List<NkaTma>> nkaTmaMap = nkaTmaService.getAllNkaTmaMap();
        Map<String, Integer> nkaMap = lkaService.getAllNkaMap();
        List<FormatType> formatTypeList = formatTypeService.getAllFormats();
        List<String> tgList = new ArrayList<>(Arrays.asList("Майонез", "Кетчуп" , "Соус"));
        modelAndView.addObject("nkaTmaMap", nkaTmaMap);
        modelAndView.addObject("nkaMap", nkaMap);
        modelAndView.addObject("formatTypeList", formatTypeList);
        modelAndView.addObject("tgList", tgList);
        modelAndView.addObject("pageTitle", "Акции сетей");
        modelAndView.addObject("pageCategory", "nkaParam");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }


    @PostMapping("/reports/nka/getNkaTmaRow")
    public ModelAndView getNkaTmaRow() {
        ModelAndView modelAndView = new ModelAndView("nka/ajax_parts/nkaTmaRow");
        List<FormatType> formatTypeList = formatTypeService.getAllFormats();
        modelAndView.addObject("formatTypeList", formatTypeList);

        return modelAndView;
    }


    @PostMapping(value = "/reports/nka/saveNewTmaList", produces = "application/json")
    @ResponseBody
    public Map<String, Integer> saveNkaParams (@RequestParam ("tmaList")String nkaTmaListJson) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return LocalDate.parse(json.getAsString());
            }
        }).create();
        Type type = new TypeToken<List<NkaTma>>(){}.getType();
        List<NkaTma> nkaTmaList = gson.fromJson(nkaTmaListJson, type);

        int answer = nkaTmaService.writeNewNkaTma(nkaTmaList);
        return Collections.singletonMap("answer", answer);
    }


    @PostMapping(value = "/reports/nka/getTmaPlan", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, Map<String,String>> getNkaTmaList (@RequestParam ("nkaId") int nkaId,
                                       @RequestParam ("formatId") int formatId,
                                       @RequestParam ("clientId") int clientId,
                                       @RequestParam ("dateFrom") String startDateS,
                                       @RequestParam ("dateTo") String endDateS) {
        LocalDate startDate = LocalDate.parse(startDateS);
        LocalDate endDate = LocalDate.parse(endDateS);
        Map<String, Map<String, String>> result = nkaTmaService.getNkaTmaByDates(nkaId, startDate, endDate, formatId, clientId);
        return result;
    }
}
