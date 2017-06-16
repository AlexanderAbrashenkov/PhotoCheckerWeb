package com.photochecker.controllers.dmp;

import com.photochecker.excelViews.DmpExcelReportView;
import com.photochecker.service.lkaDmp.DmpExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
public class DmpExcelReportController {

    @Autowired
    private DmpExcelReportService dmpExcelReportService;

    @GetMapping(value = "/reports/lkaDmp/getExcelReport",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ModelAndView getExcelReport (HttpServletRequest request,
                                        HttpServletResponse response) {

        Map<String, Object> excelParams = new HashMap<>();
        excelParams.put("dateFrom", request.getParameter("dateFrom"));
        excelParams.put("dateTo", request.getParameter("dateTo"));
        excelParams.put("dmpExcelReportService", dmpExcelReportService);
        return new ModelAndView(new DmpExcelReportView(), "excelParams", excelParams);
    }
}
