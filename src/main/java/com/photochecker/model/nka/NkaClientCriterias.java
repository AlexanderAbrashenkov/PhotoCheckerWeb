package com.photochecker.model.nka;

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

    private boolean mzDmA;
    private boolean kDmA;
    private boolean sDmA;

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
                              boolean mzDmA, boolean kDmA, boolean sDmA,
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

    public boolean isMzDmA() {
        return mzDmA;
    }

    public void setMzDmA(boolean mzDmA) {
        this.mzDmA = mzDmA;
    }

    public boolean iskDmA() {
        return kDmA;
    }

    public void setkDmA(boolean kDmA) {
        this.kDmA = kDmA;
    }

    public boolean issDmA() {
        return sDmA;
    }

    public void setsDmA(boolean sDmA) {
        this.sDmA = sDmA;
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
        return "NkaClientCriterias{" +
                "clientId=" + clientId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", saveDate=" + saveDate +
                ", mzDP=" + mzDP +
                ", mzBB=" + mzBB +
                ", mzMR=" + mzMR +
                ", mzComment='" + mzComment + '\'' +
                ", kDP=" + kDP +
                ", kBB=" + kBB +
                ", kMR=" + kMR +
                ", kComment='" + kComment + '\'' +
                ", sDP=" + sDP +
                ", sBB=" + sBB +
                ", sMR=" + sMR +
                ", sComment='" + sComment + '\'' +
                ", mzDouble=" + mzDouble +
                ", kDouble=" + kDouble +
                ", sDouble=" + sDouble +
                ", mzDmA=" + mzDmA +
                ", kDmA=" + kDmA +
                ", sDmA=" + sDmA +
                ", mzDmNa=" + mzDmNa +
                ", kDmNa=" + kDmNa +
                ", sDmNa=" + sDmNa +
                '}';
    }
}
