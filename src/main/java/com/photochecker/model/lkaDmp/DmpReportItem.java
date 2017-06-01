package com.photochecker.model.lkaDmp;

import com.photochecker.model.lka.ClientCriterias;

import java.time.LocalDate;

/**
 * Created by market6 on 29.05.2017.
 */
public class DmpReportItem {
    private String region;
    private String distr;
    private String channel;
    private int lkaId;
    private String lkaName;
    private String clientType;
    private int clientId;
    private String clientName;
    private String clientAddress;
    private DmpClientCriterias dmpClientCriterias;
    private LocalDate photo_date;

    public DmpReportItem() {
    }

    public DmpReportItem(String region, String distr, String channel, int lkaId, String lkaName,
                         String clientType, int clientId, String clientName, String clientAddress,
                         DmpClientCriterias dmpClientCriterias, LocalDate photo_date) {
        this.region = region;
        this.distr = distr;
        this.channel = channel;
        this.lkaId = lkaId;
        this.lkaName = lkaName;
        this.clientType = clientType;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.dmpClientCriterias = dmpClientCriterias;
        this.photo_date = photo_date;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public DmpClientCriterias getDmpClientCriterias() {
        return dmpClientCriterias;
    }

    public void setDmpClientCriterias(DmpClientCriterias dmpClientCriterias) {
        this.dmpClientCriterias = dmpClientCriterias;
    }

    public LocalDate getPhoto_date() {
        return photo_date;
    }

    public void setPhoto_date(LocalDate photo_date) {
        this.photo_date = photo_date;
    }
}
