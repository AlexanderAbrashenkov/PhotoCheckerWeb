package com.photochecker.service.mlka.daoImpl;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.dao.mlka.MlkaReportItemDao;
import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.model.common.User;
import com.photochecker.model.mlka.MlkaReportItem;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.service.mlka.MlkaExcelReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MlkaExcelReportServiceDaoImpl implements MlkaExcelReportService {

    @Autowired
    private MlkaReportItemDao mlkaReportItemDao;
    @Autowired
    private NkaRespDao nkaRespDao;

    @Override
    public Workbook getExcelReport(Workbook workbook, LocalDate dateFrom, LocalDate dateTo, User user) {
        List<MlkaReportItem> mlkaReportItemList = mlkaReportItemDao.findAllByDatesAndRepType(dateFrom, dateTo, 2);

        if (user.getRole() == 1) {
            List<NkaResp> nkaRespList = nkaRespDao.findAllByUser(user);
            List<String> x5DistrList = nkaRespList.stream()
                    .filter(nkaResp -> nkaResp.getNkaType().getId() == 1)
                    .map(nkaResp -> nkaResp.getDistr().getName())
                    .collect(Collectors.toList());
            List<String> tanderDistrList = nkaRespList.stream()
                    .filter(nkaResp -> nkaResp.getNkaType().getId() == 2)
                    .map(nkaResp -> nkaResp.getDistr().getName())
                    .collect(Collectors.toList());

            List<MlkaReportItem> newMlkaRepItems = mlkaReportItemList.stream()
                    .filter(mlkaReportItem -> {
                        return (mlkaReportItem.getNkaName().equals("X5")
                                && x5DistrList.contains(mlkaReportItem.getDistr()))
                                ||
                                (mlkaReportItem.getNkaName().equals("Тандер")
                                && tanderDistrList.contains(mlkaReportItem.getDistr()));
                    })
                    .collect(Collectors.toList());
            mlkaReportItemList = newMlkaRepItems;
        }

        ApachePoiManager.createApachePoi(2);
        ApachePoi apachePoi = ApachePoiManager.getInstance();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        apachePoi.createReportFile(workbook, dateFrom.format(formatter), dateTo.format(formatter));

        //X5 sheet
        String sheetName = "X5";

        List<MlkaReportItem> x5mlkaReportItemList = mlkaReportItemList.stream()
                .filter(mlkaReportItem -> mlkaReportItem.getNkaName().equals("X5"))
                .collect(Collectors.toList());

        if (x5mlkaReportItemList.size() > 0) {
            apachePoi.createConcreteSheet(sheetName, null);
            for (MlkaReportItem mlkaReportItem : x5mlkaReportItemList) {
                apachePoi.writeOneTtToConcreteSheet(new ArrayList(Arrays.asList("X5", mlkaReportItem)));
            }

            apachePoi.calcSumRowConcreteSheet("X5");
        }

        // Tander sheet
        sheetName = "Тандер";

        List<MlkaReportItem> tanderMlkaReportItemList = mlkaReportItemList.stream()
                .filter(mlkaReportItem -> mlkaReportItem.getNkaName().equals("Тандер"))
                .collect(Collectors.toList());

        if (tanderMlkaReportItemList.size() > 0) {
            apachePoi.createConcreteSheet(sheetName, null);
            for (MlkaReportItem mlkaReportItem : tanderMlkaReportItemList) {
                apachePoi.writeOneTtToConcreteSheet(new ArrayList(Arrays.asList("Тандер", mlkaReportItem)));
            }

            apachePoi.calcSumRowConcreteSheet("Тандер");
        }

        // Total sheet
        apachePoi.createTotalSheet(null);

        if (x5mlkaReportItemList.size() > 0) {
            apachePoi.createTotalSheetHeader("X5");
            Set<String> x5MlkaSet = x5mlkaReportItemList.stream()
                    .map(mlkaReportItem -> mlkaReportItem.getMlkaName())
                    .distinct()
                    .collect(Collectors.toSet());

            Set<String> sortedSet = new TreeSet<>();
            sortedSet.addAll(x5MlkaSet);

            for (String mlka : sortedSet) {
                ArrayList<Object> parameters = new ArrayList<>();
                parameters.add(mlka);
                parameters.add("X5");
                apachePoi.writeOneTtToTotalSheet(parameters);
            }
            apachePoi.calcSumRowTotalSheet();
        }

        if (tanderMlkaReportItemList.size() > 0) {
            apachePoi.createTotalSheetHeader("Тандер");
            Set<String> tanderMlkaSet = tanderMlkaReportItemList.stream()
                    .map(mlkaReportItem -> mlkaReportItem.getMlkaName())
                    .distinct()
                    .collect(Collectors.toSet());

            Set<String> sortedSet = new TreeSet<>();
            sortedSet.addAll(tanderMlkaSet);

            for (String mlka : sortedSet) {
                ArrayList<Object> parameters = new ArrayList<>();
                parameters.add(mlka);
                parameters.add("Тандер");
                apachePoi.writeOneTtToTotalSheet(parameters);
            }
            apachePoi.calcSumRowTotalSheet();
        }

        return workbook;
    }
}
