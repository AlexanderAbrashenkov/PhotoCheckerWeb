package com.photochecker.controllers.lka;

import com.photochecker.model.common.User;
import com.photochecker.service.lka.ExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class ExcelReportController {

    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping(value = "/reports/lka/getExcelReport")
    public void getExcelReport (HttpServletRequest request,
                                        HttpServletResponse response) {


        LocalDate dateFrom = LocalDate.parse(request.getParameter("dateFrom"));
        LocalDate dateTo = LocalDate.parse(request.getParameter("dateTo"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition",
                "attachment; filename=report LKA " + dateFrom.format(formatter) + "-" + dateTo.format(formatter) + ".xlsx");

        try {
            OutputStream out = response.getOutputStream();
            excelReportService.getExcelReport(out, dateFrom, dateTo, (User) request.getSession().getAttribute("user"));
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
