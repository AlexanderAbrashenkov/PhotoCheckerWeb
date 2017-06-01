package com.photochecker.model.mlka;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by market6 on 01.06.2017.
 */
public class MlkaClientCriterias {
    private int clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime saveDate;

    private boolean mzPhoto;
    private boolean mzCorr;
    private boolean mzCrit1;
    private boolean mzCrit2;
    private boolean mzCrit3;

    private boolean kPhoto;
    private boolean kCorr;
    private boolean kCrit1;
    private boolean kCrit2;
    private boolean kCrit3;

    private boolean sPhoto;
    private boolean sCorr;
    private boolean sCrit1;
    private boolean sCrit2;
    private boolean sCrit3;

    private boolean oos;
    private String comment;

    public MlkaClientCriterias() {
    }

    public MlkaClientCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo, LocalDateTime saveDate,
                               boolean mzPhoto, boolean mzCorr, boolean mzCrit1, boolean mzCrit2, boolean mzCrit3,
                               boolean kPhoto, boolean kCorr, boolean kCrit1, boolean kCrit2, boolean kCrit3,
                               boolean sPhoto, boolean sCorr, boolean sCrit1, boolean sCrit2, boolean sCrit3,
                               boolean oos, String comment) {
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.saveDate = saveDate;

        this.mzPhoto = mzPhoto;
        this.mzCorr = mzCorr;
        this.mzCrit1 = mzCrit1;
        this.mzCrit2 = mzCrit2;
        this.mzCrit3 = mzCrit3;

        this.kPhoto = kPhoto;
        this.kCorr = kCorr;
        this.kCrit1 = kCrit1;
        this.kCrit2 = kCrit2;
        this.kCrit3 = kCrit3;

        this.sPhoto = sPhoto;
        this.sCorr = sCorr;
        this.sCrit1 = sCrit1;
        this.sCrit2 = sCrit2;
        this.sCrit3 = sCrit3;

        this.oos = oos;
        this.comment = comment;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LocalDateTime getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(LocalDateTime saveDate) {
        this.saveDate = saveDate;
    }

    public boolean isMzPhoto() {
        return mzPhoto;
    }

    public void setMzPhoto(boolean mzPhoto) {
        this.mzPhoto = mzPhoto;
    }

    public boolean isMzCorr() {
        return mzCorr;
    }

    public void setMzCorr(boolean mzCorr) {
        this.mzCorr = mzCorr;
    }

    public boolean isMzCrit1() {
        return mzCrit1;
    }

    public void setMzCrit1(boolean mzCrit1) {
        this.mzCrit1 = mzCrit1;
    }

    public boolean isMzCrit2() {
        return mzCrit2;
    }

    public void setMzCrit2(boolean mzCrit2) {
        this.mzCrit2 = mzCrit2;
    }

    public boolean isMzCrit3() {
        return mzCrit3;
    }

    public void setMzCrit3(boolean mzCrit3) {
        this.mzCrit3 = mzCrit3;
    }

    public boolean iskPhoto() {
        return kPhoto;
    }

    public void setkPhoto(boolean kPhoto) {
        this.kPhoto = kPhoto;
    }

    public boolean iskCorr() {
        return kCorr;
    }

    public void setkCorr(boolean kCorr) {
        this.kCorr = kCorr;
    }

    public boolean iskCrit1() {
        return kCrit1;
    }

    public void setkCrit1(boolean kCrit1) {
        this.kCrit1 = kCrit1;
    }

    public boolean iskCrit2() {
        return kCrit2;
    }

    public void setkCrit2(boolean kCrit2) {
        this.kCrit2 = kCrit2;
    }

    public boolean iskCrit3() {
        return kCrit3;
    }

    public void setkCrit3(boolean kCrit3) {
        this.kCrit3 = kCrit3;
    }

    public boolean issPhoto() {
        return sPhoto;
    }

    public void setsPhoto(boolean sPhoto) {
        this.sPhoto = sPhoto;
    }

    public boolean issCorr() {
        return sCorr;
    }

    public void setsCorr(boolean sCorr) {
        this.sCorr = sCorr;
    }

    public boolean issCrit1() {
        return sCrit1;
    }

    public void setsCrit1(boolean sCrit1) {
        this.sCrit1 = sCrit1;
    }

    public boolean issCrit2() {
        return sCrit2;
    }

    public void setsCrit2(boolean sCrit2) {
        this.sCrit2 = sCrit2;
    }

    public boolean issCrit3() {
        return sCrit3;
    }

    public void setsCrit3(boolean sCrit3) {
        this.sCrit3 = sCrit3;
    }

    public boolean isOos() {
        return oos;
    }

    public void setOos(boolean oos) {
        this.oos = oos;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MlkaClientCriterias that = (MlkaClientCriterias) o;

        if (clientId != that.clientId) return false;
        if (!dateFrom.equals(that.dateFrom)) return false;
        return dateTo.equals(that.dateTo);
    }

    @Override
    public int hashCode() {
        int result = clientId;
        result = 31 * result + dateFrom.hashCode();
        result = 31 * result + dateTo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MlkaClientCriterias{" +
                "clientId=" + clientId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", saveDate=" + saveDate +
                ", mzPhoto=" + mzPhoto +
                ", mzCorr=" + mzCorr +
                ", mzCrit1=" + mzCrit1 +
                ", mzCrit2=" + mzCrit2 +
                ", mzCrit3=" + mzCrit3 +
                ", kPhoto=" + kPhoto +
                ", kCorr=" + kCorr +
                ", kCrit1=" + kCrit1 +
                ", kCrit2=" + kCrit2 +
                ", kCrit3=" + kCrit3 +
                ", sPhoto=" + sPhoto +
                ", sCorr=" + sCorr +
                ", sCrit1=" + sCrit1 +
                ", sCrit2=" + sCrit2 +
                ", sCrit3=" + sCrit3 +
                ", oos=" + oos +
                ", comment='" + comment + '\'' +
                '}';
    }
}
