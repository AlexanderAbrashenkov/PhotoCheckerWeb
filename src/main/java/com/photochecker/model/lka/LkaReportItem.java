package com.photochecker.model.lka;

import java.time.LocalDate;

/**
 * Created by market6 on 05.05.2017.
 */
public class LkaReportItem {
    private String region;
    private String distr;
    private int lkaId;
    private String lkaName;
    private String clientType;
    private int clientId;
    private String clientName;
    private String clientAddress;
    private ClientCriterias clientCriterias;
    private LocalDate photo_date;

    public LkaReportItem(String region, String distr, int lkaId, String lkaName, String clientType,
                         int clientId, String clientName, String clientAddress,
                         ClientCriterias clientCriterias, LocalDate photo_date) {
        this.region = region;
        this.distr = distr;
        this.lkaId = lkaId;
        this.lkaName = lkaName;
        this.clientType = clientType;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientCriterias = clientCriterias;
        this.photo_date = photo_date;
    }

    public LkaReportItem() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistr() {
        return distr;
    }

    public void setDistr(String distr) {
        this.distr = distr;
    }

    public int getLkaId() {
        return lkaId;
    }

    public void setLkaId(int lkaId) {
        this.lkaId = lkaId;
    }

    public String getLkaName() {
        return lkaName;
    }

    public void setLkaName(String lkaName) {
        this.lkaName = lkaName;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public ClientCriterias getClientCriterias() {
        return clientCriterias;
    }

    public void setClientCriterias(ClientCriterias clientCriterias) {
        this.clientCriterias = clientCriterias;
    }

    public LocalDate getPhoto_date() {
        return photo_date;
    }

    public void setPhoto_date(LocalDate photo_date) {
        this.photo_date = photo_date;
    }
}
