package com.photochecker.servlets.mlka;

import com.photochecker.model.common.User;
import com.photochecker.service.lka.ExcelReportService;
import com.photochecker.service.mlka.MlkaExcelReportService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by market6 on 05.05.2017.
 */
@WebServlet(name = "MlkaExcelReportServlet",
    urlPatterns = "/reports/mlka/getExcelReport")
public class MlkaExcelReportServlet extends HttpServlet {

    private MlkaExcelReportService mlkaExcelReportService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        mlkaExcelReportService = (MlkaExcelReportService) context.getBean("mlkaExcelReportService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate dateFrom = LocalDate.parse(request.getParameter("dateFrom"));
        LocalDate dateTo = LocalDate.parse(request.getParameter("dateTo"));

        XSSFWorkbook wb = mlkaExcelReportService.getExcelReport(dateFrom, dateTo, (User) request.getSession().getAttribute("user"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
        wb.write(outByteStream);
        byte [] outArray = outByteStream.toByteArray();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentLength(outArray.length);
        response.setHeader("Expires:", "0");
        response.setHeader("Content-Disposition", "attachment; filename=report NKA MLKA " + dateFrom.format(formatter) +
                "-" + dateTo.format(formatter) + ".xlsx");
        OutputStream outStream = response.getOutputStream();
        outStream.write(outArray);
        outStream.flush();
    }
}
