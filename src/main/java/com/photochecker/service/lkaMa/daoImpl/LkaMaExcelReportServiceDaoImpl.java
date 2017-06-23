package com.photochecker.service.lkaMa.daoImpl;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.lkaMa.LkaMaReportItemDao;
import com.photochecker.model.common.Responsibility;
import com.photochecker.model.common.User;
import com.photochecker.model.lkaMa.LkaMaReportItem;
import com.photochecker.service.lkaMa.LkaMaExcelReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LkaMaExcelReportServiceDaoImpl implements LkaMaExcelReportService {

    @Autowired
    private LkaMaReportItemDao lkaMaReportItemDao;
    @Autowired
    private ResponsibilityDao responsibilityDao;

    @Override
    public Workbook getExcelReport(Workbook workbook, LocalDate dateFrom, LocalDate dateTo, User user) {
        List<LkaMaReportItem> lkaMaReportItemList = lkaMaReportItemDao.findAllByDatesAndRepType(dateFrom, dateTo, 5);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);
            List<String> allowedDistrNames = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 5)
                    .map(resp -> resp.getDistr().getName())
                    .distinct()
                    .collect(Collectors.toList());
            lkaMaReportItemList.removeIf(lkaReportItem -> !allowedDistrNames.contains(lkaReportItem.getDistr()));
        }

        ApachePoiManager.createApachePoi(5);
        ApachePoi apachePoi = ApachePoiManager.getInstance();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        apachePoi.createReportFile(workbook, dateFrom.format(formatter), dateTo.format(formatter));

        String sheetName = "LKA MA";
        if (user.getRole() == 1) {
            sheetName = user.getUserName().substring(0, user.getUserName().indexOf(" ") + 2);
        }

        apachePoi.createConcreteSheet(sheetName, null);
        for (LkaMaReportItem lkaMaReportItem : lkaMaReportItemList) {
            apachePoi.writeOneTtToConcreteSheet(new ArrayList(Arrays.asList(lkaMaReportItem)));
        }

        apachePoi.calcSumRowConcreteSheet("LKA MA");
        workbook = apachePoi.endWriting("LKA MA");

        return workbook;
    }
}
