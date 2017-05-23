package com.photochecker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public PhotoCard() {
    }

    public PhotoCard(int clientId, String url, LocalDateTime date, LocalDateTime dateAdd,
                     String comment, boolean checked, ReportType reportType) {
        this.clientId = clientId;
        this.url = url;
        this.date = date;
        this.dateAdd = dateAdd;
        this.comment = comment;
        this.checked = checked;
        this.reportType = reportType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoCard photoCard = (PhotoCard) o;

        return url != null ? url.equals(photoCard.url) : photoCard.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
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
                '}';
    }
}
