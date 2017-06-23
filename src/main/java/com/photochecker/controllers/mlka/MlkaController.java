package com.photochecker.controllers.mlka;

import com.photochecker.model.common.*;
import com.photochecker.model.mlka.Employee;
import com.photochecker.model.mlka.NkaType;
import com.photochecker.service.common.*;
import com.photochecker.service.mlka.EmployeeService;
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
public class MlkaController {

    private final int REP_TYPE_INDEX = 2;

    @Autowired
    private CommonService commonService;
    @Autowired
    private NkaTypeService nkaTypeService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private DistrService distrService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ClientCardService clientCardService;
    @Autowired
    private PhotoCardService photoCardService;


    @GetMapping("/reports/mlka")
    public ModelAndView showMlkaPage(HttpSession session,
                                     @Value("${resVer}") String resVer) {
        ModelAndView modelAndView = new ModelAndView("mlka/mlkaPage");

        LocalDate startDate = commonService.getInitialStartDate();
        LocalDate endDate = commonService.getInitialEndDate();
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate", endDate);

        List<NkaType> nkaTypeList = nkaTypeService.getNkaTypes((User) session.getAttribute("user"),
                startDate, endDate, REP_TYPE_INDEX);

        modelAndView.addObject("nkaTypeList", nkaTypeList);
        modelAndView.addObject("pageTitle", "Фотоотчет MLKA");
        modelAndView.addObject("pageCategory", "mlka");
        modelAndView.addObject("resVer", resVer);

        return modelAndView;
    }


    @PostMapping("/reports/mlka/getNka")
    public ModelAndView nkaAjax(@RequestParam("dateFrom") String dateFromS,
                                @RequestParam("dateTo") String dateToS,
                                HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("mlka/ajax_parts/nkaOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        User user = (User) session.getAttribute("user");
        List<NkaType> nkaTypeList = nkaTypeService.getNkaTypes(user, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("nkaTypeList", nkaTypeList);
        return modelAndView;
    }


    @PostMapping("/reports/mlka/getRegions")
    public ModelAndView regionsAjax(@RequestParam("dateFrom") String dateFromS,
                                    @RequestParam("dateTo") String dateToS,
                                    @RequestParam("nkaId") int nkaId,
                                    HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("mlka/ajax_parts/regionOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        User user = (User) session.getAttribute("user");
        List<Region> regionList = regionService.getRegions(user, dateFrom, dateTo, REP_TYPE_INDEX, nkaId);
        modelAndView.addObject("regionList", regionList);
        return modelAndView;
    }


    @PostMapping("/reports/mlka/getDistrs")
    public ModelAndView distrsAjax(@RequestParam("dateFrom") String dateFromS,
                                   @RequestParam("dateTo") String dateToS,
                                   @RequestParam("nkaId") int nkaId,
                                   @RequestParam("regionId") int regionId,
                                   HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("mlka/ajax_parts/distrOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        User user = (User) session.getAttribute("user");
        List<Distr> distrList = distrService.getDistrs(user, regionId, dateFrom, dateTo, REP_TYPE_INDEX, nkaId);
        modelAndView.addObject("distrList", distrList);
        return modelAndView;
    }


    @PostMapping("/reports/mlka/getMlkas")
    public ModelAndView mlkaAjax(@RequestParam("dateFrom") String dateFromS,
                                 @RequestParam("dateTo") String dateToS,
                                 @RequestParam("nkaId") int nkaId,
                                 @RequestParam("distrId") int distrId) {

        ModelAndView modelAndView = new ModelAndView("mlka/ajax_parts/mlkaOptions");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<Employee> employeeList = employeeService.getEmployees(distrId, dateFrom, dateTo, REP_TYPE_INDEX, nkaId);
        modelAndView.addObject("mlkaList", employeeList);
        return modelAndView;
    }


    @PostMapping("/reports/mlka/getClients")
    public ModelAndView clientCardsAjax (@RequestParam("dateFrom") String dateFromS,
                                         @RequestParam("dateTo") String dateToS,
                                         @RequestParam("nkaId") int nkaId,
                                         @RequestParam("distrId") int distrId,
                                         @RequestParam("mlkaId") int mlkaId) {

        ModelAndView modelAndView = new ModelAndView("mlka/ajax_parts/addressTable");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<ClientCard> clientCardList = clientCardService.getClientCardList(distrId, mlkaId, dateFrom, dateTo, REP_TYPE_INDEX, nkaId);
        modelAndView.addObject("clientsList", clientCardList);
        return modelAndView;
    }


    @PostMapping("/reports/mlka/getPhotos")
    public ModelAndView photosAjax(@RequestParam("dateFrom") String dateFromS,
                                   @RequestParam("dateTo") String dateToS,
                                   @RequestParam("clientId") int clientId) {

        ModelAndView modelAndView = new ModelAndView("mlka/ajax_parts/photoPane");

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        List<PhotoCard> photoCardList = photoCardService.getPhotoList(clientId, dateFrom, dateTo, REP_TYPE_INDEX);
        modelAndView.addObject("photoList", photoCardList);
        return modelAndView;
    }
}
