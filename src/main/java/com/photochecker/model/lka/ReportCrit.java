package com.photochecker.model.lka;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by market6 on 30.03.2017.
 */
public class ReportCrit {
    private int clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime saveDate;

    private boolean hasMz;
    private Integer hasPhotoMz;
    private Integer correctMz;
    private Integer hasAddProdMz;
    private Integer crit1Mz;
    private Integer crit2Mz;

    private boolean hasK;
    private Integer hasPhotoK;
    private Integer correctK;
    private Integer crit1K;
    private Integer crit2K;

    private boolean hasS;
    private Integer hasPhotoS;
    private Integer correctS;
    private Integer crit1S;
    private Integer crit2S;

    private boolean hasM;
    private Integer hasPhotoM;
    private Integer correctM;
    private Integer crit1M;
    private Integer crit2M;

    private Integer oos;
    private String comment;

    public ReportCrit() {
    }

    public ReportCrit(int clientId, LocalDate dateFrom, LocalDate dateTo, LocalDateTime saveDate,
                      boolean hasMz, Integer hasPhotoMz, Integer correctMz, Integer hasAddProdMz,
                      Integer crit1Mz, Integer crit2Mz,
                      boolean hasK, Integer hasPhotoK, Integer correctK, Integer crit1K, Integer crit2K,
                      boolean hasS, Integer hasPhotoS, Integer correctS, Integer crit1S, Integer crit2S,
                      boolean hasM, Integer hasPhotoM, Integer correctM, Integer crit1M, Integer crit2M,
                      Integer oos, String comment) {
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.saveDate = saveDate;

        this.hasMz = hasMz;
        this.hasPhotoMz = hasPhotoMz;
        this.correctMz = correctMz;
        this.hasAddProdMz = hasAddProdMz;
        this.crit1Mz = crit1Mz;
        this.crit2Mz = crit2Mz;

        this.hasK = hasK;
        this.hasPhotoK = hasPhotoK;
        this.correctK = correctK;
        this.crit1K = crit1K;
        this.crit2K = crit2K;

        this.hasS = hasS;
        this.hasPhotoS = hasPhotoS;
        this.correctS = correctS;
        this.crit1S = crit1S;
        this.crit2S = crit2S;

        this.hasM = hasM;
        this.hasPhotoM = hasPhotoM;
        this.correctM = correctM;
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

    public String getSaveDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return saveDate.format(formatter);
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

    public Integer getHasPhotoMz() {
        return hasPhotoMz;
    }

    public void setHasPhotoMz(Integer hasPhotoMz) {
        this.hasPhotoMz = hasPhotoMz;
    }

    public Integer getCorrectMz() {
        return correctMz;
    }

    public void setCorrectMz(Integer correctMz) {
        this.correctMz = correctMz;
    }

    public Integer getHasAddProdMz() {
        return hasAddProdMz;
    }

    public void setHasAddProdMz(Integer hasAddProdMz) {
        this.hasAddProdMz = hasAddProdMz;
    }

    public Integer getCrit1Mz() {
        return crit1Mz;
    }

    public void setCrit1Mz(Integer crit1Mz) {
        this.crit1Mz = crit1Mz;
    }

    public Integer getCrit2Mz() {
        return crit2Mz;
    }

    public void setCrit2Mz(Integer crit2Mz) {
        this.crit2Mz = crit2Mz;
    }

    public boolean isHasK() {
        return hasK;
    }

    public void setHasK(boolean hasK) {
        this.hasK = hasK;
    }

    public Integer getHasPhotoK() {
        return hasPhotoK;
    }

    public void setHasPhotoK(Integer hasPhotoK) {
        this.hasPhotoK = hasPhotoK;
    }

    public Integer getCorrectK() {
        return correctK;
    }

    public void setCorrectK(Integer correctK) {
        this.correctK = correctK;
    }

    public Integer getCrit1K() {
        return crit1K;
    }

    public void setCrit1K(Integer crit1K) {
        this.crit1K = crit1K;
    }

    public Integer getCrit2K() {
        return crit2K;
    }

    public void setCrit2K(Integer crit2K) {
        this.crit2K = crit2K;
    }

    public boolean isHasS() {
        return hasS;
    }

    public void setHasS(boolean hasS) {
        this.hasS = hasS;
    }

    public Integer getHasPhotoS() {
        return hasPhotoS;
    }

    public void setHasPhotoS(Integer hasPhotoS) {
        this.hasPhotoS = hasPhotoS;
    }

    public Integer getCorrectS() {
        return correctS;
    }

    public void setCorrectS(Integer correctS) {
        this.correctS = correctS;
    }

    public Integer getCrit1S() {
        return crit1S;
    }

    public void setCrit1S(Integer crit1S) {
        this.crit1S = crit1S;
    }

    public Integer getCrit2S() {
        return crit2S;
    }

    public void setCrit2S(Integer crit2S) {
        this.crit2S = crit2S;
    }

    public boolean isHasM() {
        return hasM;
    }

    public void setHasM(boolean hasM) {
        this.hasM = hasM;
    }

    public Integer getHasPhotoM() {
        return hasPhotoM;
    }

    public void setHasPhotoM(Integer hasPhotoM) {
        this.hasPhotoM = hasPhotoM;
    }

    public Integer getCorrectM() {
        return correctM;
    }

    public void setCorrectM(Integer correctM) {
        this.correctM = correctM;
    }

    public Integer getCrit1M() {
        return crit1M;
    }

    public void setCrit1M(Integer crit1M) {
        this.crit1M = crit1M;
    }

    public Integer getCrit2M() {
        return crit2M;
    }

    public void setCrit2M(Integer crit2M) {
        this.crit2M = crit2M;
    }

    public Integer getOos() {
        return oos;
    }

    public void setOos(Integer oos) {
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
                ", correctMz=" + correctMz +
                ", hasAddProdMz=" + hasAddProdMz +
                ", crit1Mz=" + crit1Mz +
                ", crit2Mz=" + crit2Mz +
                ", hasK=" + hasK +
                ", hasPhotoK=" + hasPhotoK +
                ", correctK=" + correctK +
                ", crit1K=" + crit1K +
                ", crit2K=" + crit2K +
                ", hasS=" + hasS +
                ", hasPhotoS=" + hasPhotoS +
                ", correctS=" + correctS +
                ", crit1S=" + crit1S +
                ", crit2S=" + crit2S +
                ", hasM=" + hasM +
                ", hasPhotoM=" + hasPhotoM +
                ", correctM=" + correctM +
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

        ReportCrit that = (ReportCrit) o;

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
