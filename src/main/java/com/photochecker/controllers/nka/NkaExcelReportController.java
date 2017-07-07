package com.photochecker.controllers.nka;

import com.photochecker.excelViews.MlkaExcelReportView;
import com.photochecker.service.mlka.MlkaExcelReportService;
import com.photochecker.service.nka.NkaExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Controller
public class NkaExcelReportController {

    @Autowired
    private NkaExcelReportService nkaExcelReportService;

    @GetMapping(value = "/reports/nka/getExcelReport")
    public void getExcelReport (HttpServletRequest request,
                                        HttpServletResponse response) {

        LocalDate dateFrom = LocalDate.parse(request.getParameter("dateFrom"));
        LocalDate dateTo = LocalDate.parse(request.getParameter("dateTo"));

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition",
                "attachment; filename=rjkam_" + dateFrom + "_" + dateTo + ".xlsx");

        try {
            OutputStream out = response.getOutputStream();
            nkaExcelReportService.getExcelReportItems(out, dateFrom, dateTo);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
