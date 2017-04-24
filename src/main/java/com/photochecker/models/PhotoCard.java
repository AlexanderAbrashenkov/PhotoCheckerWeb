package com.photochecker.models;

import java.time.LocalDate;

/**
 * Created by market6 on 28.03.2017.
 */
public class PhotoCard {
    private String url;
    private LocalDate date;
    private LocalDate dateAdd;
    private String comment;
    private boolean checked;

    public PhotoCard() {
    }

    public PhotoCard(String url, LocalDate date, LocalDate dateAdd, String comment, boolean checked) {
        this.url = url;
        this.date = date;
        this.dateAdd = dateAdd;
        this.comment = comment;
        this.checked = checked;
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
}
