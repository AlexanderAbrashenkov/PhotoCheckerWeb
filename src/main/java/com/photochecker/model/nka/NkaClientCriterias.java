package com.photochecker.model.nka;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by market6 on 29.06.2017.
 */
public class NkaClientCriterias {

    private int clientId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDateTime saveDate;

    private boolean mzDP;
    private boolean mzBB;
    private boolean mzMR;
    private String mzComment;

    private boolean kDP;
    private boolean kBB;
    private boolean kMR;
    private String kComment;

    private boolean sDP;
    private boolean sBB;
    private boolean sMR;
    private String sComment;

    private boolean mzDouble;
    private boolean kDouble;
    private boolean sDouble;

    private int mzDmA;
    private int kDmA;
    private int sDmA;

    private int mzDmAPlan;
    private int kDmAPlan;
    private int sDmAPlan;

    private boolean mzDmNa;
    private boolean kDmNa;
    private boolean sDmNa;

    public NkaClientCriterias() {
    }

    public NkaClientCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo, LocalDateTime saveDate,
                              boolean mzDP, boolean mzBB, boolean mzMR, String mzComment,
                              boolean kDP, boolean kBB, boolean kMR, String kComment,
                              boolean sDP, boolean sBB, boolean sMR, String sComment,
                              boolean mzDouble, boolean kDouble, boolean sDouble,
                              int mzDmA, int kDmA, int sDmA,
                              int mzDmAPlan, int kDmAPlan, int sDmAPlan,
                              boolean mzDmNa, boolean kDmNa, boolean sDmNa) {
        this.clientId = clientId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.saveDate = saveDate;
        this.mzDP = mzDP;
        this.mzBB = mzBB;
        this.mzMR = mzMR;
        this.mzComment = mzComment;
        this.kDP = kDP;
        this.kBB = kBB;
        this.kMR = kMR;
        this.kComment = kComment;
        this.sDP = sDP;
        this.sBB = sBB;
        this.sMR = sMR;
        this.sComment = sComment;
        this.mzDouble = mzDouble;
        this.kDouble = kDouble;
        this.sDouble = sDouble;
        this.mzDmA = mzDmA;
        this.kDmA = kDmA;
        this.sDmA = sDmA;
        this.mzDmAPlan = mzDmAPlan;
        this.kDmAPlan = kDmAPlan;
        this.sDmAPlan = sDmAPlan;
        this.mzDmNa = mzDmNa;
        this.kDmNa = kDmNa;
        this.sDmNa = sDmNa;
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

    public boolean isMzDP() {
        return mzDP;
    }

    public void setMzDP(boolean mzDP) {
        this.mzDP = mzDP;
    }

    public boolean isMzBB() {
        return mzBB;
    }

    public void setMzBB(boolean mzBB) {
        this.mzBB = mzBB;
    }

    public boolean isMzMR() {
        return mzMR;
    }

    public void setMzMR(boolean mzMR) {
        this.mzMR = mzMR;
    }

    public String getMzComment() {
        return mzComment;
    }

    public void setMzComment(String mzComment) {
        this.mzComment = mzComment;
    }

    public boolean iskDP() {
        return kDP;
    }

    public void setkDP(boolean kDP) {
        this.kDP = kDP;
    }

    public boolean iskBB() {
        return kBB;
    }

    public void setkBB(boolean kBB) {
        this.kBB = kBB;
    }

    public boolean iskMR() {
        return kMR;
    }

    public void setkMR(boolean kMR) {
        this.kMR = kMR;
    }

    public String getkComment() {
        return kComment;
    }

    public void setkComment(String kComment) {
        this.kComment = kComment;
    }

    public boolean issDP() {
        return sDP;
    }

    public void setsDP(boolean sDP) {
        this.sDP = sDP;
    }

    public boolean issBB() {
        return sBB;
    }

    public void setsBB(boolean sBB) {
        this.sBB = sBB;
    }

    public boolean issMR() {
        return sMR;
    }

    public void setsMR(boolean sMR) {
        this.sMR = sMR;
    }

    public String getsComment() {
        return sComment;
    }

    public void setsComment(String sComment) {
        this.sComment = sComment;
    }

    public boolean isMzDouble() {
        return mzDouble;
    }

    public void setMzDouble(boolean mzDouble) {
        this.mzDouble = mzDouble;
    }

    public boolean iskDouble() {
        return kDouble;
    }

    public void setkDouble(boolean kDouble) {
        this.kDouble = kDouble;
    }

    public boolean issDouble() {
        return sDouble;
    }

    public void setsDouble(boolean sDouble) {
        this.sDouble = sDouble;
    }

    public int getMzDmA() {
        return mzDmA;
    }

    public void setMzDmA(int mzDmA) {
        this.mzDmA = mzDmA;
    }

    public int getkDmA() {
        return kDmA;
    }

    public void setkDmA(int kDmA) {
        this.kDmA = kDmA;
    }

    public int getsDmA() {
        return sDmA;
    }

    public void setsDmA(int sDmA) {
        this.sDmA = sDmA;
    }

    public int getMzDmAPlan() {
        return mzDmAPlan;
    }

    public void setMzDmAPlan(int mzDmAPlan) {
        this.mzDmAPlan = mzDmAPlan;
    }

    public int getkDmAPlan() {
        return kDmAPlan;
    }

    public void setkDmAPlan(int kDmAPlan) {
        this.kDmAPlan = kDmAPlan;
    }

    public int getsDmAPlan() {
        return sDmAPlan;
    }

    public void setsDmAPlan(int sDmAPlan) {
        this.sDmAPlan = sDmAPlan;
    }

    public boolean isMzDmNa() {
        return mzDmNa;
    }

    public void setMzDmNa(boolean mzDmNa) {
        this.mzDmNa = mzDmNa;
    }

    public boolean iskDmNa() {
        return kDmNa;
    }

    public void setkDmNa(boolean kDmNa) {
        this.kDmNa = kDmNa;
    }

    public boolean issDmNa() {
        return sDmNa;
    }

    public void setsDmNa(boolean sDmNa) {
        this.sDmNa = sDmNa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NkaClientCriterias that = (NkaClientCriterias) o;

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
        return MoreObjects.toStringHelper(this)
                .add("clientId", clientId)
                .add("dateFrom", dateFrom)
                .add("dateTo", dateTo)
                .add("saveDate", saveDate)
                .add("mzDP", mzDP)
                .add("mzBB", mzBB)
                .add("mzMR", mzMR)
                .add("mzComment", mzComment)
                .add("kDP", kDP)
                .add("kBB", kBB)
                .add("kMR", kMR)
                .add("kComment", kComment)
                .add("sDP", sDP)
                .add("sBB", sBB)
                .add("sMR", sMR)
                .add("sComment", sComment)
                .add("mzDouble", mzDouble)
                .add("kDouble", kDouble)
                .add("sDouble", sDouble)
                .add("mzDmA", mzDmA)
                .add("kDmA", kDmA)
                .add("sDmA", sDmA)
                .add("mzDmAPlan", mzDmAPlan)
                .add("kDmAPlan", kDmAPlan)
                .add("sDmAPlan", sDmAPlan)
                .add("mzDmNa", mzDmNa)
                .add("kDmNa", kDmNa)
                .add("sDmNa", sDmNa)
                .toString();
    }
}
