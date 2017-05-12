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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by market6 on 28.04.2017.
 */
public class LkaService {
    private static DAOFactory daoFactory = DAOFactory.getDAOFactory();

    public static List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate) {
        List<Region> allRegions = new ArrayList<>();
        try {
            allRegions = daoFactory.getRegionDAO().findAllByParameters(startDate, endDate);
        } catch (PersistException e) {
            e.printStackTrace();
        }

        if (user.getRole() == 1) {

            List<Responsibility> responsibilityList = new ArrayList<>();
            try {
                responsibilityList = daoFactory.getResponsibilityDAO().findAllByParameters(user);
            } catch (PersistException e) {
                e.printStackTrace();
            }

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
        List<Distr> allDistrs = new ArrayList<>();
        try {
            allDistrs = daoFactory.getDistrDAO().findAllByParameters(dateFrom, dateTo);
        } catch (PersistException e) {
            e.printStackTrace();
        }

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = new ArrayList<>();
            try {
                responsibilityList = daoFactory.getResponsibilityDAO().findAllByParameters(user);
            } catch (PersistException e) {
                e.printStackTrace();
            }

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
        Distr distr = null;
        List<Lka> allLka = null;
        try {
            distr = daoFactory.getDistrDAO().find(distrId);
            allLka = daoFactory.getLkaDAO().findAllByParameters(distr, dateFrom, dateTo);
        } catch (PersistException e) {
            e.printStackTrace();
        }

        return allLka;
    }

    public static List<ClientCard> getClientCardList(int distrId, int lkaId, LocalDate dateFrom, LocalDate dateTo) {
        Lka lka = null;
        List<ClientCard> clientCardList = new ArrayList<>();

        try {
            lka = daoFactory.getLkaDAO().find(lkaId);
            clientCardList = daoFactory.getClientCardDAO().findAllByParameters(lka, dateFrom, dateTo);
        } catch (PersistException e) {
            e.printStackTrace();
        }

        clientCardList.removeIf(clientCard -> clientCard.getDistr().getId() != distrId);
        clientCardList.removeIf(clientCard -> clientCard.getLka().getId() != lkaId);

        return clientCardList;
    }

    public static LkaCriterias getLkaCriterias(int lkaId) {
        LkaCriterias lkaCriterias = null;
        try {
            lkaCriterias = daoFactory.getLkaCriteriasDAO().find(lkaId);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return lkaCriterias;
    }

    public static List<LkaCriterias> getAllLkaCriterias() {
        List<LkaCriterias> lkaCriteriasList = new ArrayList<>();
        try {
            lkaCriteriasList = daoFactory.getLkaCriteriasDAO().findAll();
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return lkaCriteriasList;
    }

    public static List<PhotoCard> getPhotoList(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ReportType reportType = null;
        List<PhotoCard> photoCardList = new ArrayList<>();
        try {
            reportType = daoFactory.getReportTypeDAO().find(5);
            photoCardList = daoFactory.getPhotoCardDAO().findAllByParameters(reportType, clientId, dateFrom, dateTo);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return photoCardList;
    }

    public static Lka getLkaById(int id) {
        Lka lka = null;
        try {
            lka = daoFactory.getLkaDAO().find(id);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return lka;
    }

    public static boolean saveCriterias(ClientCriterias clientCriterias) {
        List<ClientCriterias> savedClientCriterias = new ArrayList<>();
        boolean succeed = false;

        try {
            savedClientCriterias = daoFactory.getClientCriteriasDAO().findAllByParameters(clientCriterias.getClientId(),
                    clientCriterias.getDateFrom(), clientCriterias.getDateTo());

            if (savedClientCriterias.size() > 0) {
                succeed = daoFactory.getClientCriteriasDAO().update(clientCriterias);
            } else {
                succeed = daoFactory.getClientCriteriasDAO().create(clientCriterias);
            }

        } catch (PersistException e) {
            e.printStackTrace();
        }

        return succeed;
    }

    public static ClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ClientCriterias clientCriterias = null;
        try {
            clientCriterias = daoFactory.getClientCriteriasDAO().findAllByParameters(clientId, dateFrom, dateTo).get(0);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return clientCriterias;
    }

    public static XSSFWorkbook getExcelReport(LocalDate dateFrom, LocalDate dateTo, User user) {
        List<LkaReportItem> lkaReportItemList = null;

        try {
            lkaReportItemList = daoFactory.getLkaReportItemDAO().findAllByParameters(dateFrom, dateTo, 5);
        } catch (PersistException e) {
            e.printStackTrace();
        }

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = null;

            try {
                responsibilityList = daoFactory.getResponsibilityDAO().findAllByParameters(user);
            } catch (PersistException e) {
                e.printStackTrace();
            }
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
        boolean succeed = false;
        try {
            List<LkaCriterias> savedCriteriasList = daoFactory.getLkaCriteriasDAO().findAll();
            for (LkaCriterias lkaCriterias : critList) {
                if (savedCriteriasList.contains(lkaCriterias)) {
                    daoFactory.getLkaCriteriasDAO().update(lkaCriterias);
                } else {
                    daoFactory.getLkaCriteriasDAO().create(lkaCriterias);
                }
            }
            succeed = true;
        } catch (PersistException e) {
            e.printStackTrace();
        }
        return succeed;
    }

}
