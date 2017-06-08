package com.photochecker.excelViews;


import com.photochecker.model.common.User;
import com.photochecker.service.lka.ExcelReportService;
import com.photochecker.service.lkaDmp.DmpExcelReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DmpExcelReportView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {


        Map<String, Object> params = (Map<String, Object>) model.get("excelParams");
        LocalDate dateFrom = LocalDate.parse((String) params.get("dateFrom"));
        LocalDate dateTo = LocalDate.parse((String) params.get("dateTo"));
        DmpExcelReportService dmpExcelReportService = (DmpExcelReportService) params.get("dmpExcelReportService");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        response.setHeader("Content-Disposition", "attachment; filename=report DMP " + dateFrom.format(formatter) +
                        "-" + dateTo.format(formatter) + ".xlsx");

        dmpExcelReportService.getExcelReport(workbook, dateFrom, dateTo, (User) request.getSession().getAttribute("user"));
    }
}
