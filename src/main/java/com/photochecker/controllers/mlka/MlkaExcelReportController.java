package com.photochecker.controllers.mlka;

import com.photochecker.excelViews.MlkaExcelReportView;
import com.photochecker.service.mlka.MlkaExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
public class MlkaExcelReportController {

    @Autowired
    private MlkaExcelReportService mlkaExcelReportService;

    @GetMapping(value = "/reports/mlka/getExcelReport",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ModelAndView getExcelReport (HttpServletRequest request,
                                        HttpServletResponse response) {

        Map<String, Object> excelParams = new HashMap<>();
        excelParams.put("dateFrom", request.getParameter("dateFrom"));
        excelParams.put("dateTo", request.getParameter("dateTo"));
        excelParams.put("mlkaExcelReportService", mlkaExcelReportService);
        return new ModelAndView(new MlkaExcelReportView(), "excelParams", excelParams);
    }
}
