package com.photochecker.model.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by market6 on 28.03.2017.
 */
public class PhotoCard {
    private int clientId;
    private String url;
    private LocalDateTime date;
    private LocalDateTime dateAdd;
    private String comment;
    private boolean checked;
    private ReportType reportType;
    private int employeeId;
    private int tagId;
    private String tagName;

    public PhotoCard() {
    }

    public PhotoCard(int clientId, String url, LocalDateTime date, LocalDateTime dateAdd, String comment,
                     boolean checked, ReportType reportType, int employeeId, int tagId, String tagName) {
        this.clientId = clientId;
        this.url = url;
        this.date = date;
        this.dateAdd = dateAdd;
        this.comment = comment;
        this.checked = checked;
        this.reportType = reportType;
        this.employeeId = employeeId;
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(LocalDateTime dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoCard photoCard = (PhotoCard) o;

        if (!url.equals(photoCard.url)) return false;
        return reportType != null ? reportType.equals(photoCard.reportType) : photoCard.reportType == null;
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + (reportType != null ? reportType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhotoCard{" +
                "clientId=" + clientId +
                ", url='" + url + '\'' +
                ", date=" + date +
                ", dateAdd=" + dateAdd +
                ", comment='" + comment + '\'' +
                ", checked=" + checked +
                ", reportType=" + reportType +
                ", employeeId=" + employeeId +
                ", tagId=" + tagId +
                ", tagName=" + tagName +
                '}';
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.date.format(formatter);
    }

    public String getFormattedDateAdd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.dateAdd.format(formatter);
    }
}
