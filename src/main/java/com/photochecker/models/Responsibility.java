package com.photochecker.models;

/**
 * Created by market6 on 21.04.2017.
 */
public class Responsibility {
    int reportType;
    String reportName;
    int regionId;
    String regionName;
    int distrId;
    String distrName;
    String responsibleLogin;
    String responsibleName;

    public Responsibility() {
    }

    public Responsibility(int reportType, String reportName, int regionId, String regionName,
                          int distrId, String distrName, String responsibleLogin, String responsibleName) {
        this.reportType = reportType;
        this.reportName = reportName;
        this.regionId = regionId;
        this.regionName = regionName;
        this.distrId = distrId;
        this.distrName = distrName;
        this.responsibleLogin = responsibleLogin;
        this.responsibleName = responsibleName;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getDistrId() {
        return distrId;
    }

    public void setDistrId(int distrId) {
        this.distrId = distrId;
    }

    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
    }

    public String getResponsibleLogin() {
        return responsibleLogin;
    }

    public void setResponsibleLogin(String responsibleLogin) {
        this.responsibleLogin = responsibleLogin;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    @Override
    public String toString() {
        return "Responsibility{" +
                "reportType=" + reportType +
                ", reportName='" + reportName + '\'' +
                ", regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", distrId=" + distrId +
                ", distrName='" + distrName + '\'' +
                ", responsibleLogin='" + responsibleLogin + '\'' +
                ", responsibleName='" + responsibleName + '\'' +
                '}';
    }
}
