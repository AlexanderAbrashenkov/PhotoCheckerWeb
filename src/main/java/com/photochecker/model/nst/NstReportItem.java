package com.photochecker.model.nst;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstReportItem {
    private String nstObl;
    private String nstClient;
    private String nstFormat;
    private NstClientCriterias nstClientCriterias;

    public NstReportItem() {
    }

    public NstReportItem(String nstObl, String nstClient, String nstFormat, NstClientCriterias nstClientCriterias) {
        this.nstObl = nstObl;
        this.nstClient = nstClient;
        this.nstFormat = nstFormat;
        this.nstClientCriterias = nstClientCriterias;
    }

    public String getNstObl() {
        return nstObl;
    }

    public void setNstObl(String nstObl) {
        this.nstObl = nstObl;
    }

    public String getNstClient() {
        return nstClient;
    }

    public void setNstClient(String nstClient) {
        this.nstClient = nstClient;
    }

    public String getNstFormat() {
        return nstFormat;
    }

    public void setNstFormat(String nstFormat) {
        this.nstFormat = nstFormat;
    }

    public NstClientCriterias getNstClientCriterias() {
        return nstClientCriterias;
    }

    public void setNstClientCriterias(NstClientCriterias nstClientCriterias) {
        this.nstClientCriterias = nstClientCriterias;
    }
}
