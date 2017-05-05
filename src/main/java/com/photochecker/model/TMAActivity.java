package com.photochecker.model;

import java.time.LocalDate;

/**
 * Created by market6 on 18.10.2016.
 */
public class TMAActivity implements Comparable<TMAActivity> {
    private String channel;
    private String netName;
    private String tg;
    private String activityName;
    private LocalDate activityStartDate;
    private LocalDate activityEndDate;
    private boolean isSalePrice;
    private boolean hasOos;

    public TMAActivity(String channel, String netName, String tg, String activityName, LocalDate activityStartDate, LocalDate activityEndDate,
                       boolean isSalePrice, boolean hasOos) {
        this.channel = channel;
        this.netName = netName;
        this.tg = tg;
        this.activityName = activityName;
        this.activityStartDate = activityStartDate;
        this.activityEndDate = activityEndDate;
        this.isSalePrice = isSalePrice;
        this.hasOos = hasOos;
    }

    public TMAActivity() {
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public LocalDate getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(LocalDate activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public LocalDate getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(LocalDate activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public boolean isSalePrice() {
        return isSalePrice;
    }

    public void setSalePrice(boolean salePrice) {
        isSalePrice = salePrice;
    }

    public boolean isHasOos() {
        return hasOos;
    }

    public void setHasOos(boolean hasOos) {
        this.hasOos = hasOos;
    }

    @Override
    public int compareTo(TMAActivity o) {
        if (tg.equals(o.getTg())) {
            return 0;
        } else if (tg.equals("Майонез") && o.getTg().equals("Кетчуп")) {
            return -1;
        } else if (tg.equals("Майонез") && o.getTg().equals("Соус")) {
            return -1;
        } else if (tg.equals("Кетчуп") && o.getTg().equals("Соус")) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TMAActivity)) return false;

        TMAActivity activity = (TMAActivity) o;

        if (!tg.equals(activity.tg)) return false;
        return activityName.equals(activity.activityName);

    }

    @Override
    public int hashCode() {
        int result = tg.hashCode();
        result = 31 * result + activityName.hashCode();
        return result;
    }
}
