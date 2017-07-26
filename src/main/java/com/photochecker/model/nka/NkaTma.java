package com.photochecker.model.nka;

import com.google.common.base.Objects;
import com.photochecker.model.common.FormatType;
import com.photochecker.model.common.Lka;

import java.time.LocalDate;

public class NkaTma {
    private Lka lka;
    private LocalDate startDate;
    private LocalDate endDate;
    private FormatType formatType;
    private String tgName;
    private int skuCount;
    private String comment;

    public NkaTma() {
    }

    public NkaTma(Lka lka, LocalDate startDate, LocalDate endDate,
                  FormatType formatType, String tgName, int skuCount, String comment) {
        this.lka = lka;
        this.startDate = startDate;
        this.endDate = endDate;
        this.formatType = formatType;
        this.tgName = tgName;
        this.skuCount = skuCount;
        this.comment = comment;
    }

    public Lka getLka() {
        return lka;
    }

    public void setLka(Lka lka) {
        this.lka = lka;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName;
    }

    public int getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(int skuCount) {
        this.skuCount = skuCount;
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
        NkaTma nkaTma = (NkaTma) o;
        return Objects.equal(lka, nkaTma.lka) &&
                Objects.equal(startDate, nkaTma.startDate) &&
                Objects.equal(endDate, nkaTma.endDate) &&
                Objects.equal(formatType, nkaTma.formatType) &&
                Objects.equal(tgName, nkaTma.tgName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lka, startDate, endDate, formatType, tgName);
    }
}
