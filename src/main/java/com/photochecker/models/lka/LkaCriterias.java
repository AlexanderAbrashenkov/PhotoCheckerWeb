package com.photochecker.models.lka;

/**
 * Created by market6 on 29.03.2017.
 */
public class LkaCriterias {
    private String lkaName;
    private int lkaId;
    private String crit1Name;
    private int crit1Mz;
    private int crit1K;
    private int crit1S;
    private int crit1M;
    private String crit2Name;

    public LkaCriterias() {
    }

    public LkaCriterias(String lkaName, int lkaId, String crit1Name, int crit1Mz, int crit1K, int crit1S, int crit1M, String crit2Name) {
        this.lkaName = lkaName;
        this.lkaId = lkaId;
        this.crit1Name = crit1Name;
        this.crit1Mz = crit1Mz;
        this.crit1K = crit1K;
        this.crit1S = crit1S;
        this.crit1M = crit1M;
        this.crit2Name = crit2Name;
    }

    public String getLkaName() {
        return lkaName;
    }

    public void setLkaName(String lkaName) {
        this.lkaName = lkaName;
    }

    public int getLkaId() {
        return lkaId;
    }

    public void setLkaId(int lkaId) {
        this.lkaId = lkaId;
    }

    public String getCrit1Name() {
        return crit1Name;
    }

    public void setCrit1Name(String crit1Name) {
        this.crit1Name = crit1Name;
    }

    public int getCrit1Mz() {
        return crit1Mz;
    }

    public void setCrit1Mz(int crit1Mz) {
        this.crit1Mz = crit1Mz;
    }

    public int getCrit1K() {
        return crit1K;
    }

    public void setCrit1K(int crit1K) {
        this.crit1K = crit1K;
    }

    public int getCrit1S() {
        return crit1S;
    }

    public void setCrit1S(int crit1S) {
        this.crit1S = crit1S;
    }

    public int getCrit1M() {
        return crit1M;
    }

    public void setCrit1M(int crit1M) {
        this.crit1M = crit1M;
    }

    public String getCrit2Name() {
        return crit2Name;
    }

    public void setCrit2Name(String crit2Name) {
        this.crit2Name = crit2Name;
    }

    @Override
    public String toString() {
        return "LkaCriterias{" +
                "lkaName='" + lkaName + '\'' +
                ", lkaId=" + lkaId +
                ", crit1Name='" + crit1Name + '\'' +
                ", crit1Mz=" + crit1Mz +
                ", crit1K=" + crit1K +
                ", crit1S=" + crit1S +
                ", crit1M=" + crit1M +
                ", crit2Name='" + crit2Name + '\'' +
                '}';
    }
}