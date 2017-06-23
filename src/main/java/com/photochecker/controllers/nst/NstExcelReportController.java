package com.photochecker.controllers.nst;

import com.photochecker.excelViews.NstExcelReportView;
import com.photochecker.service.nst.NstExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
public class NstExcelReportController {

    @Autowired
    private NstExcelReportService nstExcelReportService;

    @GetMapping(value = "/reports/nst/getExcelReport",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ModelAndView getExcelReport (HttpServletRequest request,
                                        HttpServletResponse response) {

        Map<String, Object> excelParams = new HashMap<>();
        excelParams.put("dateFrom", request.getParameter("dateFrom"));
        excelParams.put("dateTo", request.getParameter("dateTo"));
        excelParams.put("nstExcelReportService", nstExcelReportService);
        return new ModelAndView(new NstExcelReportView(), "excelParams", excelParams);
    }
}
