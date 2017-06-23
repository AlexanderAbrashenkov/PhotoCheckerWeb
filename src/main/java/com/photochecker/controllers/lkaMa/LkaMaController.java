package com.photochecker.controllers.lkaMa;

import com.photochecker.model.common.*;
import com.photochecker.service.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class LkaMaController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private DistrService distrService;
    @Autowired
    private LkaService lkaService;
    @Autowired
    private ClientCardService clientCardService;
    @Autowired
    private PhotoCardService photoCardService;

    private final int REP_TYPE_INDEX = 6;

    @GetMapping("/reports/lka_ma")
    public ModelAndView showLkaMa(HttpSession session,
                                  @Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("lkaMa/lkaMaPage");

        LocalDate startDate = commonService.getInitialStartDate();
        LocalDate endDate = commonService.getInitialEndDate();
        modelAndView.addObject("startDate", commonService.getInitialStartDate());
        modelAndView.addObject("endDate", commonService.getInitialEndDate());

        List<Region> regionList = regionService.getRegions((User) session.getAttribute("user"),
                startDate, endDate, REP_TYPE_INDEX);
        modelAndView.addObject("regionList", regionList);
        modelAndView.addObject("pageTitle", "Фотоотчет LKA MA");
        modelAndView.addObject("pageCategory", "lkaMa");
        modelAndView.addObject("resVer", resVer);
        return modelAndView;
    }


    @PostMapping("/reports/lka_ma/getRegions")
    public ModelAndView regionsAjax (@RequestParam ("dateFrom") String dateFromS,
                                     @RequestParam ("dateTo") String dateToS,
                                     HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("lkaMa/ajax_parts/regionOptions");
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        User user = (User) session.getAttribute("user");
        List<Region> regionList = regionService.getRegions(user, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("regionList", regionList);
        return modelAndView;
    }


    @PostMapping("/reports/lka_ma/getDistrs")
    public ModelAndView distrsAjax (@RequestParam ("dateFrom") String dateFromS,
                                    @RequestParam ("dateTo") String dateToS,
                                    @RequestParam ("regionId") int regionId,
                                    HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("lkaMa/ajax_parts/distrOptions");
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        User user = (User) session.getAttribute("user");
        List<Distr> distrList = distrService.getDistrs(user, regionId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("distrList", distrList);
        return modelAndView;
    }


    @PostMapping("/reports/lka_ma/getLkas")
    public ModelAndView lkaAjax (@RequestParam ("dateFrom") String dateFromS,
                                 @RequestParam ("dateTo") String dateToS,
                                 @RequestParam ("distrId") int distrId) {

        ModelAndView modelAndView = new ModelAndView("lkaMa/ajax_parts/lkaOptions");
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<Lka> lkaList = lkaService.getLkas(distrId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("lkaList", lkaList);
        return modelAndView;
    }


    @PostMapping("/reports/lka_ma/getClients")
    public ModelAndView clientsAjax (@RequestParam ("dateFrom") String dateFromS,
                                     @RequestParam ("dateTo") String dateToS,
                                     @RequestParam ("distrId") int distrId,
                                     @RequestParam ("lkaId") int lkaId) {

        ModelAndView modelAndView = new ModelAndView("lkaMa/ajax_parts/addressTable");
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<ClientCard> clientCardList = clientCardService.getClientCardList(distrId, lkaId, 1, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("clientsList", clientCardList);
        return modelAndView;
    }


    @PostMapping("/reports/lka_ma/getPhotos")
    public ModelAndView photosAjax (@RequestParam ("dateFrom") String dateFromS,
                                    @RequestParam ("dateTo") String dateToS,
                                    @RequestParam ("clientId") int clientId) {

        ModelAndView modelAndView = new ModelAndView("lkaMa/ajax_parts/photoPane");
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<PhotoCard>  photoCardList = photoCardService.getPhotoList(clientId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("photoList", photoCardList);
        return modelAndView;
    }


    @PostMapping(value = "/reports/lka_ma/getLkaNameById", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, String> getLkaNameById (@RequestParam("lkaId") int lkaId) {
        Lka lka = lkaService.getLkaById(lkaId);
        String lkaName = lka.getName();
        return Collections.singletonMap("lkaName", lkaName);
    }
}
