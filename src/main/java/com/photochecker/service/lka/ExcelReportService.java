package com.photochecker.service.lka;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.model.lka.LkaReportItem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 17.05.2017.
 */
public interface ExcelReportService {
    public XSSFWorkbook getExcelReport(LocalDate dateFrom, LocalDate dateTo, User user);
}
