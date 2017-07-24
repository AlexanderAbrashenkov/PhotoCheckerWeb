package com.photochecker.model.nst;

/**
 * Created by market6 on 06.07.2017.
 */
public class NstStat {

    private int formatId;
    private int oblId;

    private int totalCount;
    private int totalChecked;
    private int totalCheckedToday;

    private int oblCount;
    private int oblChecked;
    private int oblCheckedToday;

    public NstStat(int formatId, int oblId, int totalCount, int totalChecked, int totalCheckedToday, int oblCount, int oblChecked, int oblCheckedToday) {
        this.formatId = formatId;
        this.oblId = oblId;
        this.totalCount = totalCount;
        this.totalChecked = totalChecked;
        this.totalCheckedToday = totalCheckedToday;
        this.oblCount = oblCount;
        this.oblChecked = oblChecked;
        this.oblCheckedToday = oblCheckedToday;
    }

    public NstStat() {
    }

    public int getFormatId() {
        return formatId;
    }

    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public int getOblId() {
        return oblId;
    }

    public void setOblId(int oblId) {
        this.oblId = oblId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalChecked() {
        return totalChecked;
    }

    public void setTotalChecked(int totalChecked) {
        this.totalChecked = totalChecked;
    }

    public int getTotalCheckedToday() {
        return totalCheckedToday;
    }

    public void setTotalCheckedToday(int totalCheckedToday) {
        this.totalCheckedToday = totalCheckedToday;
    }

    public int getOblCount() {
        return oblCount;
    }

    public void setOblCount(int oblCount) {
        this.oblCount = oblCount;
    }

    public int getOblChecked() {
        return oblChecked;
    }

    public void setOblChecked(int oblChecked) {
        this.oblChecked = oblChecked;
    }

    public int getOblCheckedToday() {
        return oblCheckedToday;
    }

    public void setOblCheckedToday(int oblCheckedToday) {
        this.oblCheckedToday = oblCheckedToday;
    }

    @Override
    public String toString() {
        return "NstStat{" +
                "totalCount=" + totalCount +
                ", totalChecked=" + totalChecked +
                ", totalCheckedToday=" + totalCheckedToday +
                ", oblCount=" + oblCount +
                ", oblChecked=" + oblChecked +
                ", oblCheckedToday=" + oblCheckedToday +
                '}';
    }
}
