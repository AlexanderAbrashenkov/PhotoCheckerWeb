package com.photochecker.model.lka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by market6 on 05.05.2017.
 */
public class LkaReportItem {
    private int index;
    private String region;
    private String distr;
    private int lkaId;
    private String lkaName;
    private String clientType;
    private int clientId;
    private String clientName;
    private String clientAddress;
    private ReportCrit clientCriterias;
    private LocalDate photo_date;
    private int empId;
    private String empName;
    private int checked;

    public LkaReportItem(String region, String distr, int lkaId, String lkaName, String clientType,
                         int clientId, String clientName, String clientAddress,
                         ReportCrit clientCriterias, LocalDate photo_date, int empId, String empName,
                         int checked) {
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
        this.empId = empId;
        this.empName = empName;
        this.checked = checked;
    }

    public LkaReportItem() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public ReportCrit getClientCriterias() {
        return clientCriterias;
    }

    public void setClientCriterias(ReportCrit clientCriterias) {
        this.clientCriterias = clientCriterias;
    }

    public LocalDate getPhoto_date() {
        return photo_date;
    }

    public String getPhotoDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return photo_date.format(formatter);
    }

    public void setPhoto_date(LocalDate photo_date) {
        this.photo_date = photo_date;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
