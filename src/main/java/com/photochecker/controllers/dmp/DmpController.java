package com.photochecker.controllers.dmp;

import com.photochecker.model.common.*;
import com.photochecker.service.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class DmpController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private DistrService distrService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private LkaService lkaService;
    @Autowired
    private ClientCardService clientCardService;
    @Autowired
    private PhotoCardService photoCardService;


    private final int REP_TYPE_INDEX = 1;

    @GetMapping("/reports/lkaDmp")
    public ModelAndView showDmpPage(HttpSession session,
                                    @Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("lkaDmp/lkaDmpPage");
        LocalDate startDate = commonService.getInitialStartDateWeek();
        LocalDate endDate = commonService.getInitialEndDateWeek();
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);

        List<Region> regionList = regionService.getRegions((User) session.getAttribute("user"),
                startDate, endDate, REP_TYPE_INDEX);
        modelAndView.addObject("regionList", regionList);
        modelAndView.addObject("pageTitle", "Фотоотчет LKA ДМП");
        modelAndView.addObject("pageCategory", "lkaDmp");
        modelAndView.addObject("resVer", resVer);

        return modelAndView;
    }


    @PostMapping("/reports/lkaDmp/getRegions")
    public ModelAndView regionsAjax(@RequestParam("dateFrom") String dateFromS,
                                    @RequestParam("dateTo") String dateToS,
                                    HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("lkaDmp/ajax_parts/regionOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        User user = (User) session.getAttribute("user");
        List<Region> regionList = regionService.getRegions(user, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("regionList", regionList);
        return modelAndView;
    }


    @PostMapping("/reports/lkaDmp/getDistrs")
    public ModelAndView distrsAjax (@RequestParam("dateFrom") String dateFromS,
                                    @RequestParam("dateTo") String dateToS,
                                    @RequestParam("regionId") int regionId,
                                    HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("lkaDmp/ajax_parts/distrOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        User user = (User) session.getAttribute("user");
        List<Distr> distrList = distrService.getDistrs(user, regionId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("distrList", distrList);
        return modelAndView;
    }


    @PostMapping("/reports/lkaDmp/getChannels")
    public ModelAndView channelsAjax(@RequestParam("dateFrom") String dateFromS,
                                     @RequestParam("dateTo") String dateToS,
                                     @RequestParam("distrId") int distrId) {

        ModelAndView modelAndView = new ModelAndView("lkaDmp/ajax_parts/channelOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<Channel> channelList = channelService.getChannels(distrId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("channelList", channelList);
        return modelAndView;
    }


    @PostMapping("/reports/lkaDmp/getLkas")
    public ModelAndView lkaAjax(@RequestParam("dateFrom") String dateFromS,
                                @RequestParam("dateTo") String dateToS,
                                @RequestParam("distrId") int distrId) {

        ModelAndView modelAndView = new ModelAndView("lkaDmp/ajax_parts/lkaOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<Lka> lkaList = lkaService.getLkas(distrId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("lkaList", lkaList);
        return modelAndView;
    }


    @PostMapping("/reports/lkaDmp/getClients")
    public ModelAndView clientCardsAjax(@RequestParam("dateFrom") String dateFromS,
                                        @RequestParam("dateTo") String dateToS,
                                        @RequestParam("distrId") int distrId,
                                        @RequestParam("channelId") int channelId,
                                        @RequestParam("lkaId") int lkaId) {

        ModelAndView modelAndView = new ModelAndView("lkaDmp/ajax_parts/addressTable");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<ClientCard> clientCardList = clientCardService.getClientCardList(distrId, lkaId, channelId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("clientsList", clientCardList);
        return modelAndView;
    }


    @PostMapping("/reports/lkaDmp/getPhotos")
    public ModelAndView photosAjax (@RequestParam("dateFrom") String dateFromS,
                                    @RequestParam("dateTo") String dateToS,
                                    @RequestParam("clientId") int clientId) {

        ModelAndView modelAndView = new ModelAndView("lkaDmp/ajax_parts/photoPane");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<PhotoCard>  photoCardList = photoCardService.getPhotoList(clientId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("photoList", photoCardList);
        return modelAndView;
    }




}
