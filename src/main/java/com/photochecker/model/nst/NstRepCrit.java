package com.photochecker.model.nst;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstRepCrit {
    private int clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime saveDate;
    private int visitCount;

    private boolean mzMatrix;
    private String mzPhoto;
    private String mzBorders;
    private String mzVert;
    private String mz30;
    private String mzCenter;
    private String mzComment;

    private boolean ksMatrix;
    private String ksPhoto;
    private String ksBorders;
    private String ksVert;
    private String ks30;
    private String ksCenter;
    private String ksComment;

    private boolean mMatrix;
    private String mPhoto;
    private String mBorders;
    private String mVert;
    private String mCenter;
    private String mComment;

    public NstRepCrit() {
    }

    public NstRepCrit(int clientId, LocalDate dateFrom, LocalDate dateTo, LocalDateTime saveDate, int visitCount,
                      boolean mzMatrix, String mzPhoto, String mzBorders, String mzVert, String mz30, String mzCenter, String mzComment,
                      boolean ksMatrix, String ksPhoto, String ksBorders, String ksVert, String ks30, String ksCenter, String ksComment,
                      boolean mMatrix, String mPhoto, String mBorders, String mVert, String mCenter, String mComment) {
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.saveDate = saveDate;
        this.visitCount = visitCount;
        this.mzMatrix = mzMatrix;
        this.mzPhoto = mzPhoto;
        this.mzBorders = mzBorders;
        this.mzVert = mzVert;
        this.mz30 = mz30;
        this.mzCenter = mzCenter;
        this.mzComment = mzComment;
        this.ksMatrix = ksMatrix;
        this.ksPhoto = ksPhoto;
        this.ksBorders = ksBorders;
        this.ksVert = ksVert;
        this.ks30 = ks30;
        this.ksCenter = ksCenter;
        this.ksComment = ksComment;
        this.mMatrix = mMatrix;
        this.mPhoto = mPhoto;
        this.mBorders = mBorders;
        this.mVert = mVert;
        this.mCenter = mCenter;
        this.mComment = mComment;
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

    public String getSaveDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return saveDate.format(formatter);
    }

    public void setSaveDate(LocalDateTime saveDate) {
        this.saveDate = saveDate;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public boolean isMzMatrix() {
        return mzMatrix;
    }

    public void setMzMatrix(boolean mzMatrix) {
        this.mzMatrix = mzMatrix;
    }

    public String getMzPhoto() {
        return mzPhoto;
    }

    public void setMzPhoto(String mzPhoto) {
        this.mzPhoto = mzPhoto;
    }

    public String getMzBorders() {
        return mzBorders;
    }

    public void setMzBorders(String mzBorders) {
        this.mzBorders = mzBorders;
    }

    public String getMzVert() {
        return mzVert;
    }

    public void setMzVert(String mzVert) {
        this.mzVert = mzVert;
    }

    public String getMz30() {
        return mz30;
    }

    public void setMz30(String mz30) {
        this.mz30 = mz30;
    }

    public String getMzCenter() {
        return mzCenter;
    }

    public void setMzCenter(String mzCenter) {
        this.mzCenter = mzCenter;
    }

    public String getMzComment() {
        return mzComment;
    }

    public void setMzComment(String mzComment) {
        this.mzComment = mzComment;
    }

    public boolean isKsMatrix() {
        return ksMatrix;
    }

    public void setKsMatrix(boolean ksMatrix) {
        this.ksMatrix = ksMatrix;
    }

    public String getKsPhoto() {
        return ksPhoto;
    }

    public void setKsPhoto(String ksPhoto) {
        this.ksPhoto = ksPhoto;
    }

    public String getKsBorders() {
        return ksBorders;
    }

    public void setKsBorders(String ksBorders) {
        this.ksBorders = ksBorders;
    }

    public String getKsVert() {
        return ksVert;
    }

    public void setKsVert(String ksVert) {
        this.ksVert = ksVert;
    }

    public String getKs30() {
        return ks30;
    }

    public void setKs30(String ks30) {
        this.ks30 = ks30;
    }

    public String getKsCenter() {
        return ksCenter;
    }

    public void setKsCenter(String ksCenter) {
        this.ksCenter = ksCenter;
    }

    public String getKsComment() {
        return ksComment;
    }

    public void setKsComment(String ksComment) {
        this.ksComment = ksComment;
    }

    public boolean ismMatrix() {
        return mMatrix;
    }

    public void setmMatrix(boolean mMatrix) {
        this.mMatrix = mMatrix;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmBorders() {
        return mBorders;
    }

    public void setmBorders(String mBorders) {
        this.mBorders = mBorders;
    }

    public String getmVert() {
        return mVert;
    }

    public void setmVert(String mVert) {
        this.mVert = mVert;
    }

    public String getmCenter() {
        return mCenter;
    }

    public void setmCenter(String mCenter) {
        this.mCenter = mCenter;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NstRepCrit that = (NstRepCrit) o;

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
        return "NstClientCriterias{" +
                "clientId=" + clientId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", saveDate=" + saveDate +
                ", visitCount=" + visitCount +
                ", mzMatrix=" + mzMatrix +
                ", mzPhoto=" + mzPhoto +
                ", mzBorders=" + mzBorders +
                ", mzVert=" + mzVert +
                ", mz30=" + mz30 +
                ", mzCenter=" + mzCenter +
                ", mzComment='" + mzComment + '\'' +
                ", ksMatrix=" + ksMatrix +
                ", ksPhoto=" + ksPhoto +
                ", ksBorders=" + ksBorders +
                ", ksVert=" + ksVert +
                ", ks30=" + ks30 +
                ", ksCenter=" + ksCenter +
                ", ksComment='" + ksComment + '\'' +
                ", mMatrix=" + mMatrix +
                ", mPhoto=" + mPhoto +
                ", mBorders=" + mBorders +
                ", mVert=" + mVert +
                ", mCenter=" + mCenter +
                ", mComment='" + mComment + '\'' +
                '}';
    }
}
