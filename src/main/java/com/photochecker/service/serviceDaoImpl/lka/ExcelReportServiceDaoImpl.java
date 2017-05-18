package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.lka.LkaReportItemDao;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;
import com.photochecker.model.lka.LkaReportItem;
import com.photochecker.service.lka.ExcelReportService;
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
public class ExcelReportServiceDaoImpl implements ExcelReportService {

    private LkaReportItemDao lkaReportItemDao;
    private ResponsibilityDao responsibilityDao;

    public ExcelReportServiceDaoImpl() {
        lkaReportItemDao = DaoFactory.getDAOFactory().getLkaReportItemDAO();
        responsibilityDao = DaoFactory.getDAOFactory().getResponsibilityDAO();
    }

    @Override
    public XSSFWorkbook getExcelReport(LocalDate dateFrom, LocalDate dateTo, User user) {
        List<LkaReportItem> lkaReportItemList = lkaReportItemDao.findAllByDatesAndRepType(dateFrom, dateTo, 5);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);
            List<String> allowedDistrNames = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 5)
                    .map(resp -> resp.getDistr().getName())
                    .distinct()
                    .collect(Collectors.toList());
            lkaReportItemList.removeIf(lkaReportItem -> !allowedDistrNames.contains(lkaReportItem.getDistr()));
        }

        ApachePoiManager.createApachePoi(5);
        ApachePoi apachePoi = ApachePoiManager.getInstance();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        apachePoi.createReportFile(dateFrom.format(formatter), dateTo.format(formatter));

        String sheetName = "LKA";
        if (user.getRole() == 1) {
            sheetName = user.getUserName().substring(0, user.getUserName().indexOf(" ") + 2);
        }

        apachePoi.createConcreteSheet(sheetName, null);
        for (LkaReportItem lkaReportItem : lkaReportItemList) {
            apachePoi.writeOneTtToConcreteSheet(new ArrayList(Arrays.asList(lkaReportItem)));
        }

        apachePoi.calcSumRowConcreteSheet("LKA");
        XSSFWorkbook workbook = apachePoi.endWriting("LKA");

        return workbook;
    }
}
