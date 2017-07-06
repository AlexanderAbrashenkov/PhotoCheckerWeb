package com.photochecker.controllers.nka;

import com.photochecker.model.common.*;
import com.photochecker.model.mlka.Employee;
import com.photochecker.model.mlka.NkaType;
import com.photochecker.service.common.*;
import com.photochecker.service.mlka.NkaTypeService;
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
public class NkaController {

    private final int REP_TYPE_INDEX = 3;

    @Autowired
    private CommonService commonService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LkaService lkaService;
    @Autowired
    private ClientCardService clientCardService;
    @Autowired
    private PhotoCardService photoCardService;


    @GetMapping("/reports/nka")
    public ModelAndView showNkaPage(@Value("${resVer}") String resVer) {
        ModelAndView modelAndView = new ModelAndView("nka/nkaPage");

        LocalDate startDate = commonService.getInitialStartDateWeek();
        LocalDate endDate = commonService.getInitialEndDateWeek();
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);

        List<Employee> rjkamList = employeeService.getEmployees(startDate, endDate, REP_TYPE_INDEX);

        modelAndView.addObject("rjkamList", rjkamList);
        modelAndView.addObject("pageTitle", "Фотоотчет RJKAM");
        modelAndView.addObject("pageCategory", "nka");
        modelAndView.addObject("resVer", resVer);

        return modelAndView;
    }


    @PostMapping("/reports/nka/getRjkam")
    public ModelAndView rjkamAjax(@RequestParam("dateFrom") String dateFromS,
                                @RequestParam("dateTo") String dateToS) {

        ModelAndView modelAndView = new ModelAndView("nka/ajax_parts/rjkamOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<Employee> rjkamList = employeeService.getEmployees(dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("rjkamList", rjkamList);
        return modelAndView;
    }


    @PostMapping("/reports/nka/getNka")
    public ModelAndView nkaAjax(@RequestParam("dateFrom") String dateFromS,
                                    @RequestParam("dateTo") String dateToS,
                                    @RequestParam("rjkamId") int rjkamId) {

        ModelAndView modelAndView = new ModelAndView("nka/ajax_parts/nkaOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<Lka> nkaList = lkaService.getLkaByRjkam(dateFrom, dateTo, REP_TYPE_INDEX, rjkamId);
        modelAndView.addObject("nkaList", nkaList);
        return modelAndView;
    }


    @PostMapping("/reports/nka/getClients")
    public ModelAndView clientCardsAjax (@RequestParam("dateFrom") String dateFromS,
                                         @RequestParam("dateTo") String dateToS,
                                         @RequestParam("rjkamId") int rjkamId,
                                         @RequestParam("nkaId") int nkaId) {

        ModelAndView modelAndView = new ModelAndView("nka/ajax_parts/addressTable");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<ClientCard> clientCardList = clientCardService.getClientCardListByRjkam(rjkamId, nkaId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("clientsList", clientCardList);
        return modelAndView;
    }


    @PostMapping("/reports/nka/getPhotos")
    public ModelAndView photosAjax(@RequestParam("dateFrom") String dateFromS,
                                   @RequestParam("dateTo") String dateToS,
                                   @RequestParam("clientId") int clientId) {

        ModelAndView modelAndView = new ModelAndView("nka/ajax_parts/photoPane");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<PhotoCard> photoCardList = photoCardService.getPhotoList(clientId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("photoList", photoCardList);
        return modelAndView;
    }
}
