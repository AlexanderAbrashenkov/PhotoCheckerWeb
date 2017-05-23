package com.photochecker.model;

/**
 * Created by market6 on 21.04.2017.
 */
public class Responsibility {
    private ReportType reportType;
    private Distr distr;
    private User user;

    public Responsibility() {
    }

    public Responsibility(ReportType reportType, Distr distr, User user) {
        this.reportType = reportType;
        this.distr = distr;
        this.user = user;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public Distr getDistr() {
        return distr;
    }

    public void setDistr(Distr distr) {
        this.distr = distr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Responsibility that = (Responsibility) o;

        if (!reportType.equals(that.reportType)) return false;
        return distr.equals(that.distr);
    }

    @Override
    public int hashCode() {
        int result = reportType.hashCode();
        result = 31 * result + distr.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Responsibility{" +
                "reportType=" + reportType +
                ", distr=" + distr +
                ", user=" + user +
                '}';
    }
}
