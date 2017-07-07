package com.photochecker.service.nka;

import com.photochecker.model.common.User;
import com.photochecker.model.nka.NkaRjkamReportItem;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface NkaExcelReportService {
    public void getExcelReportItems(OutputStream out, LocalDate dateFrom, LocalDate dateTo);
}
