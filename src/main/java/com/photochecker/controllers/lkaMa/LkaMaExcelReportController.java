package com.photochecker.controllers.lkaMa;

import com.photochecker.excelViews.LkaMaExcelReportView;
import com.photochecker.service.lkaMa.LkaMaExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LkaMaExcelReportController {

    @Autowired
    private LkaMaExcelReportService lkaMaExcelReportService;

    @GetMapping(value = "/reports/lka_ma/getExcelReport",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ModelAndView getExcelReport (HttpServletRequest request,
                                        HttpServletResponse response) {

        Map<String, Object> excelParams = new HashMap<>();
        excelParams.put("dateFrom", request.getParameter("dateFrom"));
        excelParams.put("dateTo", request.getParameter("dateTo"));
        excelParams.put("lkaMaExcelReportService", lkaMaExcelReportService);
        return new ModelAndView(new LkaMaExcelReportView(), "excelParams", excelParams);
    }
}
