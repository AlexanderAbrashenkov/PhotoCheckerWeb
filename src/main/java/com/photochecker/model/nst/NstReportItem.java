package com.photochecker.model.nst;

import com.google.common.collect.ComparisonChain;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstReportItem implements Comparable<NstReportItem> {
    private int index;
    private int nstOblId;
    private String nstObl;
    private int nstClientId;
    private String nstClient;
    private int nstFormatId;
    private String nstFormat;
    private NstRepCrit nstRepCrit;

    public NstReportItem() {
    }

    public NstReportItem(int nstOblId, String nstObl, int nstClientId, String nstClient, int nstFormatId, String nstFormat, NstRepCrit nstRepCrit) {
        this.nstOblId = nstOblId;
        this.nstObl = nstObl;
        this.nstClientId = nstClientId;
        this.nstClient = nstClient;
        this.nstFormatId = nstFormatId;
        this.nstFormat = nstFormat;
        this.nstRepCrit = nstRepCrit;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNstOblId() {
        return nstOblId;
    }

    public void setNstOblId(int nstOblId) {
        this.nstOblId = nstOblId;
    }

    public String getNstObl() {
        return nstObl;
    }

    public void setNstObl(String nstObl) {
        this.nstObl = nstObl;
    }

    public int getNstClientId() {
        return nstClientId;
    }

    public void setNstClientId(int nstClientId) {
        this.nstClientId = nstClientId;
    }

    public String getNstClient() {
        return nstClient;
    }

    public void setNstClient(String nstClient) {
        this.nstClient = nstClient;
    }

    public int getNstFormatId() {
        return nstFormatId;
    }

    public void setNstFormatId(int nstFormatId) {
        this.nstFormatId = nstFormatId;
    }

    public String getNstFormat() {
        return nstFormat;
    }

    public void setNstFormat(String nstFormat) {
        this.nstFormat = nstFormat;
    }

    public NstRepCrit getNstRepCrit() {
        return nstRepCrit;
    }

    public void setNstRepCrit(NstRepCrit nstRepCrit) {
        this.nstRepCrit = nstRepCrit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NstReportItem that = (NstReportItem) o;

        if (nstOblId != that.nstOblId) return false;
        if (nstClientId != that.nstClientId) return false;
        return nstFormatId == that.nstFormatId;
    }

    @Override
    public int hashCode() {
        int result = nstOblId;
        result = 31 * result + nstClientId;
        result = 31 * result + nstFormatId;
        return result;
    }

    @Override
    public int compareTo(NstReportItem o) {
        return ComparisonChain.start()
                .compare(nstFormatId, o.getNstFormatId())
                .compare(nstObl, o.getNstObl())
                .compare(nstClient, o.getNstClient())
                .result();
    }
}
