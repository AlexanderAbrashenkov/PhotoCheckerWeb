package com.photochecker.apache_poi;

import com.photochecker.model.lka.LkaCriterias;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 13.01.2017.
 */
public interface ApachePoi {

    void setReportPath(String reportPath);

    void createReportFile(String dateFrom, String dateTo);

    XSSFWorkbook endWriting(String net);

    //ArrayList<TMAActivity> getTMAActivityFromFile(int photoType, String netName, LocalDate dateFrom, LocalDate dateTo) throws IOException;

    void createConcreteSheet(String partForHeader, List activities);

    void writeOneTtToConcreteSheet(List parameters);

    void calcSumRowConcreteSheet(String partForHeader);

    void createTotalSheet(String partForHeader);

    void createTotalSheetHeader(String partForHeader);

    void writeOneTtToTotalSheet(List parameters);

    void calcSumRowTotalSheet();

    List<LkaCriterias> getLkaCriteriaList() throws IOException;
}
