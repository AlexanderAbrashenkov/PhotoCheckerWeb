package com.photochecker.service.lkaDmp.daoImpl;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.lkaDmp.DmpReportItemDao;
import com.photochecker.model.common.Responsibility;
import com.photochecker.model.common.User;
import com.photochecker.model.lkaDmp.DmpReportItem;
import com.photochecker.service.lkaDmp.DmpExcelReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 29.05.2017.
 */
public class DmpExcelReportServiceDaoImpl implements DmpExcelReportService {

    @Autowired
    private DmpReportItemDao dmpReportItemDao;
    @Autowired
    private ResponsibilityDao responsibilityDao;

    @Override
    public Workbook getExcelReport(Workbook workbook, LocalDate dateFrom, LocalDate dateTo, User user) {
        List<DmpReportItem> dmpReportItemList = dmpReportItemDao.findAllByDatesAndRepType(dateFrom, dateTo, 1);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);
            List<String> allowedDistrNames = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 1)
                    .map(resp -> resp.getDistr().getName())
                    .distinct()
                    .collect(Collectors.toList());
            dmpReportItemList.removeIf(dmpReportItem -> !allowedDistrNames.contains(dmpReportItem.getDistr()));
        }

        ApachePoiManager.createApachePoi(1);
        ApachePoi apachePoi = ApachePoiManager.getInstance();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        apachePoi.createReportFile(workbook, dateFrom.format(formatter), dateTo.format(formatter));

        String sheetName = "DMP";
        if (user.getRole() == 1) {
            sheetName = user.getUserName().substring(0, user.getUserName().indexOf(" ") + 2);
        }

        apachePoi.createConcreteSheet(sheetName, null);
        for (DmpReportItem dmpReportItem : dmpReportItemList) {
            apachePoi.writeOneTtToConcreteSheet(new ArrayList(Arrays.asList(dmpReportItem)));
        }

        apachePoi.calcSumRowConcreteSheet("DMP");

        return workbook;
    }
}
