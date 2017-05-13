package com.photochecker.service;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.dao.DAOFactory;
import com.photochecker.model.*;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.model.lka.LkaCriterias;
import com.photochecker.model.lka.LkaReportItem;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 28.04.2017.
 */
public class LkaService {
    private static DAOFactory daoFactory = DAOFactory.getDAOFactory();

    public static List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate) {
        List<Region> allRegions = daoFactory.getRegionDAO().findAllByDates(startDate, endDate);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = daoFactory.getResponsibilityDAO().findAllByUser(user);

            List<Region> allowedRegions = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 5)
                    .map(resp -> resp.getDistr().getRegion())
                    .distinct()
                    .collect(Collectors.toList());
            allRegions.removeIf(region -> !allowedRegions.contains(region));
        }

        return allRegions;
    }

    public static List<Distr> getDistrs(User user, int regionId, LocalDate dateFrom, LocalDate dateTo) {
        List<Distr> allDistrs = daoFactory.getDistrDAO().findAllByDates(dateFrom, dateTo);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = daoFactory.getResponsibilityDAO().findAllByUser(user);

            List<Distr> allowedDistrs = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 5)
                    .map(resp -> resp.getDistr())
                    .distinct()
                    .collect(Collectors.toList());
            allDistrs.removeIf(distr -> !allowedDistrs.contains(distr));
        }

        allDistrs.removeIf(distr -> distr.getRegion().getId() != regionId);
        return allDistrs;
    }

    public static List<Lka> getLkas(int distrId, LocalDate dateFrom, LocalDate dateTo) {
        Distr distr = daoFactory.getDistrDAO().find(distrId);
        List<Lka> allLka = daoFactory.getLkaDAO().findAllByDistrAndDates(distr, dateFrom, dateTo);

        return allLka;
    }

    public static List<ClientCard> getClientCardList(int distrId, int lkaId, LocalDate dateFrom, LocalDate dateTo) {
        Lka lka = null;
        lka = daoFactory.getLkaDAO().find(lkaId);
        List<ClientCard> clientCardList = daoFactory.getClientCardDAO().findAllByLkaAndDates(lka, dateFrom, dateTo);

        clientCardList.removeIf(clientCard -> clientCard.getDistr().getId() != distrId);
        clientCardList.removeIf(clientCard -> clientCard.getLka().getId() != lkaId);

        return clientCardList;
    }

    public static LkaCriterias getLkaCriterias(int lkaId) {
        LkaCriterias lkaCriterias = daoFactory.getLkaCriteriasDAO().find(lkaId);
        return lkaCriterias;
    }

    public static List<LkaCriterias> getAllLkaCriterias() {
        List<LkaCriterias> lkaCriteriasList = daoFactory.getLkaCriteriasDAO().findAll();
        return lkaCriteriasList;
    }

    public static List<PhotoCard> getPhotoList(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ReportType reportType = daoFactory.getReportTypeDAO().find(5);
        List<PhotoCard> photoCardList = daoFactory.getPhotoCardDAO().findAllByRepClientDates(reportType, clientId, dateFrom, dateTo);

        return photoCardList;
    }

    public static Lka getLkaById(int id) {
        Lka lka = daoFactory.getLkaDAO().find(id);
        return lka;
    }

    public static boolean saveCriterias(ClientCriterias clientCriterias) {
        boolean succeed;
        List<ClientCriterias> savedClientCriterias = daoFactory.getClientCriteriasDAO().findAllByClientAndDates(clientCriterias.getClientId(),
                clientCriterias.getDateFrom(), clientCriterias.getDateTo());

        if (null != savedClientCriterias && savedClientCriterias.size() > 0) {
            succeed = daoFactory.getClientCriteriasDAO().update(clientCriterias);
        } else {
            succeed = daoFactory.getClientCriteriasDAO().create(clientCriterias);
        }

        return succeed;
    }

    public static ClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ClientCriterias clientCriterias = daoFactory.getClientCriteriasDAO().findAllByClientAndDates(clientId, dateFrom, dateTo).get(0);
        return clientCriterias;
    }

    public static XSSFWorkbook getExcelReport(LocalDate dateFrom, LocalDate dateTo, User user) {
        List<LkaReportItem> lkaReportItemList = daoFactory.getLkaReportItemDAO().findAllByDatesAndRepType(dateFrom, dateTo, 5);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = daoFactory.getResponsibilityDAO().findAllByUser(user);
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

    public static boolean writeNewLkaCriterias(List<LkaCriterias> critList) {
        boolean succeed;
        List<LkaCriterias> savedCriteriasList = daoFactory.getLkaCriteriasDAO().findAll();
        for (LkaCriterias lkaCriterias : critList) {
            if (savedCriteriasList.contains(lkaCriterias)) {
                daoFactory.getLkaCriteriasDAO().update(lkaCriterias);
            } else {
                daoFactory.getLkaCriteriasDAO().create(lkaCriterias);
            }
        }
        succeed = true;
        return succeed;
    }

}
