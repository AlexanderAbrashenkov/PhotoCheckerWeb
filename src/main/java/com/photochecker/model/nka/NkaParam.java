package com.photochecker.model.nka;

/**
 * Created by market6 on 29.06.2017.
 */
public class NkaParam {

    private int nkaId;
    private String nkaName;

    private String mzDpShort;
    private String mzDpFull;
    private String mzBbShort;
    private String mzBbFull;
    private String mzMrShort;
    private String mzMrFull;

    private String kDpShort;
    private String kDpFull;
    private String kBbShort;
    private String kBbFull;
    private String kMrShort;
    private String kMrFull;

    private String sDpShort;
    private String sDpFull;
    private String sBbShort;
    private String sBbFull;
    private String sMrShort;
    private String sMrFull;

    public NkaParam() {
    }

    public NkaParam(int nkaId, String nkaName) {
        this.nkaId = nkaId;
        this.nkaName = nkaName;
        this.mzDpShort = "";
        this.mzDpFull = "";
        this.mzBbShort = "";
        this.mzBbFull = "";
        this.mzMrShort = "";
        this.mzMrFull = "";
        this.kDpShort = "";
        this.kDpFull = "";
        this.kBbShort = "";
        this.kBbFull = "";
        this.kMrShort = "";
        this.kMrFull = "";
        this.sDpShort = "";
        this.sDpFull = "";
        this.sBbShort = "";
        this.sBbFull = "";
        this.sMrShort = "";
        this.sMrFull = "";
    }

    public NkaParam(int nkaId, String nkaName,
                    String mzDpShort, String mzDpFull,
                    String mzBbShort, String mzBbFull,
                    String mzMrShort, String mzMrFull,
                    String kDpShort, String kDpFull,
                    String kBbShort, String kBbFull,
                    String kMrShort, String kMrFull,
                    String sDpShort, String sDpFull,
                    String sBbShort, String sBbFull,
                    String sMrShort, String sMrFull) {
        this.nkaId = nkaId;
        this.nkaName = nkaName;
        this.mzDpShort = mzDpShort;
        this.mzDpFull = mzDpFull;
        this.mzBbShort = mzBbShort;
        this.mzBbFull = mzBbFull;
        this.mzMrShort = mzMrShort;
        this.mzMrFull = mzMrFull;
        this.kDpShort = kDpShort;
        this.kDpFull = kDpFull;
        this.kBbShort = kBbShort;
        this.kBbFull = kBbFull;
        this.kMrShort = kMrShort;
        this.kMrFull = kMrFull;
        this.sDpShort = sDpShort;
        this.sDpFull = sDpFull;
        this.sBbShort = sBbShort;
        this.sBbFull = sBbFull;
        this.sMrShort = sMrShort;
        this.sMrFull = sMrFull;
    }

    public int getNkaId() {
        return nkaId;
    }

    public void setNkaId(int nkaId) {
        this.nkaId = nkaId;
    }

    public String getNkaName() {
        return nkaName;
    }

    public void setNkaName(String nkaName) {
        this.nkaName = nkaName;
    }

    public String getMzDpShort() {
        return mzDpShort;
    }

    public void setMzDpShort(String mzDpShort) {
        this.mzDpShort = mzDpShort;
    }

    public String getMzDpFull() {
        return mzDpFull;
    }

    public void setMzDpFull(String mzDpFull) {
        this.mzDpFull = mzDpFull;
    }

    public String getMzBbShort() {
        return mzBbShort;
    }

    public void setMzBbShort(String mzBbShort) {
        this.mzBbShort = mzBbShort;
    }

    public String getMzBbFull() {
        return mzBbFull;
    }

    public void setMzBbFull(String mzBbFull) {
        this.mzBbFull = mzBbFull;
    }

    public String getMzMrShort() {
        return mzMrShort;
    }

    public void setMzMrShort(String mzMrShort) {
        this.mzMrShort = mzMrShort;
    }

    public String getMzMrFull() {
        return mzMrFull;
    }

    public void setMzMrFull(String mzMrFull) {
        this.mzMrFull = mzMrFull;
    }

    public String getkDpShort() {
        return kDpShort;
    }

    public void setkDpShort(String kDpShort) {
        this.kDpShort = kDpShort;
    }

    public String getkDpFull() {
        return kDpFull;
    }

    public void setkDpFull(String kDpFull) {
        this.kDpFull = kDpFull;
    }

    public String getkBbShort() {
        return kBbShort;
    }

    public void setkBbShort(String kBbShort) {
        this.kBbShort = kBbShort;
    }

    public String getkBbFull() {
        return kBbFull;
    }

    public void setkBbFull(String kBbFull) {
        this.kBbFull = kBbFull;
    }

    public String getkMrShort() {
        return kMrShort;
    }

    public void setkMrShort(String kMrShort) {
        this.kMrShort = kMrShort;
    }

    public String getkMrFull() {
        return kMrFull;
    }

    public void setkMrFull(String kMrFull) {
        this.kMrFull = kMrFull;
    }

    public String getsDpShort() {
        return sDpShort;
    }

    public void setsDpShort(String sDpShort) {
        this.sDpShort = sDpShort;
    }

    public String getsDpFull() {
        return sDpFull;
    }

    public void setsDpFull(String sDpFull) {
        this.sDpFull = sDpFull;
    }

    public String getsBbShort() {
        return sBbShort;
    }

    public void setsBbShort(String sBbShort) {
        this.sBbShort = sBbShort;
    }

    public String getsBbFull() {
        return sBbFull;
    }

    public void setsBbFull(String sBbFull) {
        this.sBbFull = sBbFull;
    }

    public String getsMrShort() {
        return sMrShort;
    }

    public void setsMrShort(String sMrShort) {
        this.sMrShort = sMrShort;
    }

    public String getsMrFull() {
        return sMrFull;
    }

    public void setsMrFull(String sMrFull) {
        this.sMrFull = sMrFull;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NkaParam nkaParam = (NkaParam) o;

        return nkaId == nkaParam.nkaId;
    }

    @Override
    public int hashCode() {
        return nkaId;
    }

    @Override
    public String toString() {
        return "NkaParam{" +
                "nkaId=" + nkaId +
                ", nkaName='" + nkaName + '\'' +
                ", mzDpShort='" + mzDpShort + '\'' +
                ", mzDpFull='" + mzDpFull + '\'' +
                ", mzBbShort='" + mzBbShort + '\'' +
                ", mzBbFull='" + mzBbFull + '\'' +
                ", mzMrShort='" + mzMrShort + '\'' +
                ", mzMrFull='" + mzMrFull + '\'' +
                ", kDpShort='" + kDpShort + '\'' +
                ", kDpFull='" + kDpFull + '\'' +
                ", kBbShort='" + kBbShort + '\'' +
                ", kBbFull='" + kBbFull + '\'' +
                ", kMrShort='" + kMrShort + '\'' +
                ", kMrFull='" + kMrFull + '\'' +
                ", sDpShort='" + sDpShort + '\'' +
                ", sDpFull='" + sDpFull + '\'' +
                ", sBbShort='" + sBbShort + '\'' +
                ", sBbFull='" + sBbFull + '\'' +
                ", sMrShort='" + sMrShort + '\'' +
                ", sMrFull='" + sMrFull + '\'' +
                '}';
    }
}
