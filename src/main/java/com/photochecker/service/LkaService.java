package com.photochecker.service;

import com.photochecker.dao.DAOFactory;
import com.photochecker.model.*;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.model.lka.LkaCriterias;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 28.04.2017.
 */
public class LkaService {
    private static DAOFactory daoFactory = DAOFactory.getDAOFactory();

    public static LocalDate getInitialStartDate() {
        LocalDate startDate = LocalDate.now().minusDays(2);
        return startDate;
    }

    public static LocalDate getInitialEndDate() {
        LocalDate endDate = LocalDate.now().minusDays(2);
        return endDate;
    }

    public static List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate) {
        List<Region> allRegions = daoFactory.getRegionDAO().findAllByDates(startDate, endDate);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = daoFactory.getResponsibilityDAO().find(user);
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
            List<Responsibility> responsibilityList = daoFactory.getResponsibilityDAO().find(user);
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
        List<Lka> allLka = daoFactory.getLkaDAO().findAllByDatesAndDistr(distr, dateFrom, dateTo);
        return allLka;
    }

    public static List<ClientCard> getClientCardList(int distrId, int lkaId, LocalDate dateFrom, LocalDate dateTo) {
        Lka lka = daoFactory.getLkaDAO().find(lkaId);
        List<ClientCard> clientCardList = daoFactory.getClientCardDAO().findAllByDatesAndLka(lka, dateFrom, dateTo);

        clientCardList.removeIf(clientCard -> clientCard.getDistr().getId() != distrId);
        clientCardList.removeIf(clientCard -> clientCard.getLka().getId() != lkaId);

        return clientCardList;
    }

    public static LkaCriterias getLkaCriterias(int lkaId) {
        Lka lka = daoFactory.getLkaDAO().find(lkaId);
        LkaCriterias lkaCriterias = daoFactory.getLkaCriteriasDAO().find(lka);
        return lkaCriterias;
    }

    public static List<PhotoCard> getPhotoList(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ReportType reportType = daoFactory.getReportTypeDAO().find(5);
        List<PhotoCard> photoCardList = daoFactory.getPhotoCardDAO().findAllByDateClientRepType(reportType, clientId, dateFrom, dateTo);
        return photoCardList;
    }

    public static boolean saveCriterias(ClientCriterias clientCriterias) {
        ClientCriterias savedClientCriterias = daoFactory.getClientCriteriasDAO().find(clientCriterias.getClientId(),
                clientCriterias.getDateFrom(), clientCriterias.getDateTo());
        boolean succeed = false;
        if (savedClientCriterias != null) {
            succeed = daoFactory.getClientCriteriasDAO().update(clientCriterias);
        } else {
            succeed = daoFactory.getClientCriteriasDAO().create(clientCriterias);
        }
        return succeed;
    }
}
