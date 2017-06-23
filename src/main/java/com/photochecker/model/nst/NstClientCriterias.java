package com.photochecker.model.nst;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstClientCriterias {
    private int clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime saveDate;
    private int visitCount;

    private boolean mzMatrix;
    private boolean mzPhoto;
    private boolean mzBorders;
    private boolean mzVert;
    private boolean mz30;
    private boolean mzCenter;
    private String mzComment;

    private boolean ksMatrix;
    private boolean ksPhoto;
    private boolean ksBorders;
    private boolean ksVert;
    private boolean ks30;
    private boolean ksCenter;
    private String ksComment;

    private boolean mMatrix;
    private boolean mPhoto;
    private boolean mBorders;
    private boolean mVert;
    private boolean mCenter;
    private String mComment;

    public NstClientCriterias() {
    }

    public NstClientCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo, LocalDateTime saveDate, int visitCount,
                              boolean mzMatrix, boolean mzPhoto, boolean mzBorders,
                              boolean mzVert, boolean mz30, boolean mzCenter, String mzComment,
                              boolean ksMatrix, boolean ksPhoto, boolean ksBorders,
                              boolean ksVert, boolean ks30, boolean ksCenter, String ksComment,
                              boolean mMatrix, boolean mPhoto, boolean mBorders,
                              boolean mVert, boolean mCenter, String mComment) {
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

    public LocalDateTime getSaveDate() {
        return saveDate;
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

    public boolean isMzPhoto() {
        return mzPhoto;
    }

    public void setMzPhoto(boolean mzPhoto) {
        this.mzPhoto = mzPhoto;
    }

    public boolean isMzBorders() {
        return mzBorders;
    }

    public void setMzBorders(boolean mzBorders) {
        this.mzBorders = mzBorders;
    }

    public boolean isMzVert() {
        return mzVert;
    }

    public void setMzVert(boolean mzVert) {
        this.mzVert = mzVert;
    }

    public boolean isMz30() {
        return mz30;
    }

    public void setMz30(boolean mz30) {
        this.mz30 = mz30;
    }

    public boolean isMzCenter() {
        return mzCenter;
    }

    public void setMzCenter(boolean mzCenter) {
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

    public boolean isKsPhoto() {
        return ksPhoto;
    }

    public void setKsPhoto(boolean ksPhoto) {
        this.ksPhoto = ksPhoto;
    }

    public boolean isKsBorders() {
        return ksBorders;
    }

    public void setKsBorders(boolean ksBorders) {
        this.ksBorders = ksBorders;
    }

    public boolean isKsVert() {
        return ksVert;
    }

    public void setKsVert(boolean ksVert) {
        this.ksVert = ksVert;
    }

    public boolean isKs30() {
        return ks30;
    }

    public void setKs30(boolean ks30) {
        this.ks30 = ks30;
    }

    public boolean isKsCenter() {
        return ksCenter;
    }

    public void setKsCenter(boolean ksCenter) {
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

    public boolean ismPhoto() {
        return mPhoto;
    }

    public void setmPhoto(boolean mPhoto) {
        this.mPhoto = mPhoto;
    }

    public boolean ismBorders() {
        return mBorders;
    }

    public void setmBorders(boolean mBorders) {
        this.mBorders = mBorders;
    }

    public boolean ismVert() {
        return mVert;
    }

    public void setmVert(boolean mVert) {
        this.mVert = mVert;
    }

    public boolean ismCenter() {
        return mCenter;
    }

    public void setmCenter(boolean mCenter) {
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

        NstClientCriterias that = (NstClientCriterias) o;

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
