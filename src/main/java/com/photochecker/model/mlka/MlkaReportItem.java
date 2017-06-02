package com.photochecker.model.mlka;

import com.photochecker.model.lka.ClientCriterias;

import java.time.LocalDate;

/**
 * Created by market6 on 05.05.2017.
 */
public class MlkaReportItem {
    private String nkaName;
    private String region;
    private String distr;
    private String mlkaName;
    private String clientType;
    private int clientId;
    private String clientName;
    private String clientAddress;
    private MlkaClientCriterias mlkaClientCriterias;
    private LocalDate photo_date;

    public MlkaReportItem() {
    }

    public MlkaReportItem(String nkaName, String region, String distr, String mlkaName,
                          String clientType, int clientId, String clientName, String clientAddress,
                          MlkaClientCriterias mlkaClientCriterias, LocalDate photo_date) {
        this.nkaName = nkaName;
        this.region = region;
        this.distr = distr;
        this.mlkaName = mlkaName;
        this.clientType = clientType;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.mlkaClientCriterias = mlkaClientCriterias;
        this.photo_date = photo_date;
    }

    public String getNkaName() {
        return nkaName;
    }

    public void setNkaName(String nkaName) {
        this.nkaName = nkaName;
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

    public String getMlkaName() {
        return mlkaName;
    }

    public void setMlkaName(String mlkaName) {
        this.mlkaName = mlkaName;
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

    public MlkaClientCriterias getMlkaClientCriterias() {
        return mlkaClientCriterias;
    }

    public void setMlkaClientCriterias(MlkaClientCriterias mlkaClientCriterias) {
        this.mlkaClientCriterias = mlkaClientCriterias;
    }

    public LocalDate getPhoto_date() {
        return photo_date;
    }

    public void setPhoto_date(LocalDate photo_date) {
        this.photo_date = photo_date;
    }
}
