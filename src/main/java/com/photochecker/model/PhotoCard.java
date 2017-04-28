package com.photochecker.model;

import java.time.LocalDate;

/**
 * Created by market6 on 28.03.2017.
 */
public class PhotoCard {
    private int clientId;
    private String url;
    private LocalDate date;
    private LocalDate dateAdd;
    private String comment;
    private boolean checked;
    private ReportType reportType;

    public PhotoCard() {
    }

    public PhotoCard(int clientId, String url, LocalDate date, LocalDate dateAdd,
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(LocalDate dateAdd) {
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
}
