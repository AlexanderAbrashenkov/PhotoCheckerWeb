package com.photochecker.service.mlka;

import com.photochecker.model.common.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;

/**
 * Created by market6 on 17.05.2017.
 */
public interface MlkaExcelReportService {
    public XSSFWorkbook getExcelReport(LocalDate dateFrom, LocalDate dateTo, User user);
}
