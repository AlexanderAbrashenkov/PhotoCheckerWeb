package com.photochecker.service.lkaDmp;

import com.photochecker.model.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;

/**
 * Created by market6 on 29.05.2017.
 */
public interface DmpExcelReportService {
    XSSFWorkbook getExcelReport(LocalDate dateFrom, LocalDate dateTo, User user);
}