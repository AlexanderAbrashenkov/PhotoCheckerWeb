package com.photochecker.model.lka;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by market6 on 30.03.2017.
 */
public class ClientCriterias {
    private int clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime saveDate;

    private boolean hasMz;
    private boolean hasPhotoMz;
    private boolean isCorrectMz;
    private boolean hasAddProdMz;
    private boolean crit1Mz;
    private boolean crit2Mz;

    private boolean hasK;
    private boolean hasPhotoK;
    private boolean isCorrectK;
    private boolean crit1K;
    private boolean crit2K;

    private boolean hasS;
    private boolean hasPhotoS;
    private boolean isCorrectS;
    private boolean crit1S;
    private boolean crit2S;

    private boolean hasM;
    private boolean hasPhotoM;
    private boolean isCorrectM;
    private boolean crit1M;
    private boolean crit2M;

    private boolean oos;
    private String comment;

    public ClientCriterias() {
    }

    public ClientCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo, LocalDateTime saveDate,
                           boolean hasMz, boolean hasPhotoMz, boolean isCorrectMz, boolean hasAddProdMz,
                           boolean crit1Mz, boolean crit2Mz,
                           boolean hasK, boolean hasPhotoK, boolean isCorrectK, boolean crit1K, boolean crit2K,
                           boolean hasS, boolean hasPhotoS, boolean isCorrectS, boolean crit1S, boolean crit2S,
                           boolean hasM, boolean hasPhotoM, boolean isCorrectM, boolean crit1M, boolean crit2M,
                           boolean oos, String comment) {
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.saveDate = saveDate;

        this.hasMz = hasMz;
        this.hasPhotoMz = hasPhotoMz;
        this.isCorrectMz = isCorrectMz;
        this.hasAddProdMz = hasAddProdMz;
        this.crit1Mz = crit1Mz;
        this.crit2Mz = crit2Mz;

        this.hasK = hasK;
        this.hasPhotoK = hasPhotoK;
        this.isCorrectK = isCorrectK;
        this.crit1K = crit1K;
        this.crit2K = crit2K;

        this.hasS = hasS;
        this.hasPhotoS = hasPhotoS;
        this.isCorrectS = isCorrectS;
        this.crit1S = crit1S;
        this.crit2S = crit2S;

        this.hasM = hasM;
        this.hasPhotoM = hasPhotoM;
        this.isCorrectM = isCorrectM;
        this.crit1M = crit1M;
        this.crit2M = crit2M;

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

    public boolean isHasMz() {
        return hasMz;
    }

    public void setHasMz(boolean hasMz) {
        this.hasMz = hasMz;
    }

    public boolean isHasPhotoMz() {
        return hasPhotoMz;
    }

    public void setHasPhotoMz(boolean hasPhotoMz) {
        this.hasPhotoMz = hasPhotoMz;
    }

    public boolean isCorrectMz() {
        return isCorrectMz;
    }

    public void setCorrectMz(boolean correctMz) {
        isCorrectMz = correctMz;
    }

    public boolean isHasAddProdMz() {
        return hasAddProdMz;
    }

    public void setHasAddProdMz(boolean hasAddProdMz) {
        this.hasAddProdMz = hasAddProdMz;
    }

    public boolean isCrit1Mz() {
        return crit1Mz;
    }

    public void setCrit1Mz(boolean crit1Mz) {
        this.crit1Mz = crit1Mz;
    }

    public boolean isCrit2Mz() {
        return crit2Mz;
    }

    public void setCrit2Mz(boolean crit2Mz) {
        this.crit2Mz = crit2Mz;
    }

    public boolean isHasK() {
        return hasK;
    }

    public void setHasK(boolean hasK) {
        this.hasK = hasK;
    }

    public boolean isHasPhotoK() {
        return hasPhotoK;
    }

    public void setHasPhotoK(boolean hasPhotoK) {
        this.hasPhotoK = hasPhotoK;
    }

    public boolean isCorrectK() {
        return isCorrectK;
    }

    public void setCorrectK(boolean correctK) {
        isCorrectK = correctK;
    }

    public boolean isCrit1K() {
        return crit1K;
    }

    public void setCrit1K(boolean crit1K) {
        this.crit1K = crit1K;
    }

    public boolean isCrit2K() {
        return crit2K;
    }

    public void setCrit2K(boolean crit2K) {
        this.crit2K = crit2K;
    }

    public boolean isHasS() {
        return hasS;
    }

    public void setHasS(boolean hasS) {
        this.hasS = hasS;
    }

    public boolean isHasPhotoS() {
        return hasPhotoS;
    }

    public void setHasPhotoS(boolean hasPhotoS) {
        this.hasPhotoS = hasPhotoS;
    }

    public boolean isCorrectS() {
        return isCorrectS;
    }

    public void setCorrectS(boolean correctS) {
        isCorrectS = correctS;
    }

    public boolean isCrit1S() {
        return crit1S;
    }

    public void setCrit1S(boolean crit1S) {
        this.crit1S = crit1S;
    }

    public boolean isCrit2S() {
        return crit2S;
    }

    public void setCrit2S(boolean crit2S) {
        this.crit2S = crit2S;
    }

    public boolean isHasM() {
        return hasM;
    }

    public void setHasM(boolean hasM) {
        this.hasM = hasM;
    }

    public boolean isHasPhotoM() {
        return hasPhotoM;
    }

    public void setHasPhotoM(boolean hasPhotoM) {
        this.hasPhotoM = hasPhotoM;
    }

    public boolean isCorrectM() {
        return isCorrectM;
    }

    public void setCorrectM(boolean correctM) {
        isCorrectM = correctM;
    }

    public boolean isCrit1M() {
        return crit1M;
    }

    public void setCrit1M(boolean crit1M) {
        this.crit1M = crit1M;
    }

    public boolean isCrit2M() {
        return crit2M;
    }

    public void setCrit2M(boolean crit2M) {
        this.crit2M = crit2M;
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
    public String toString() {
        return "ClientCriterias{" +
                "clientId=" + clientId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", saveDate=" + saveDate +
                ", hasMz=" + hasMz +
                ", hasPhotoMz=" + hasPhotoMz +
                ", isCorrectMz=" + isCorrectMz +
                ", hasAddProdMz=" + hasAddProdMz +
                ", crit1Mz=" + crit1Mz +
                ", crit2Mz=" + crit2Mz +
                ", hasK=" + hasK +
                ", hasPhotoK=" + hasPhotoK +
                ", isCorrectK=" + isCorrectK +
                ", crit1K=" + crit1K +
                ", crit2K=" + crit2K +
                ", hasS=" + hasS +
                ", hasPhotoS=" + hasPhotoS +
                ", isCorrectS=" + isCorrectS +
                ", crit1S=" + crit1S +
                ", crit2S=" + crit2S +
                ", hasM=" + hasM +
                ", hasPhotoM=" + hasPhotoM +
                ", isCorrectM=" + isCorrectM +
                ", crit1M=" + crit1M +
                ", crit2M=" + crit2M +
                ", oos=" + oos +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientCriterias that = (ClientCriterias) o;

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
}
