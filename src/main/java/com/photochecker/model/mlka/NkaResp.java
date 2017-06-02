package com.photochecker.model.mlka;

import com.photochecker.model.common.Distr;
import com.photochecker.model.common.User;

/**
 * Created by market6 on 01.06.2017.
 */
public class NkaResp implements Comparable<NkaResp> {
    private NkaType nkaType;
    private Distr distr;
    private User user;

    public NkaResp() {
    }

    public NkaResp(NkaType nkaType, Distr distr, User user) {
        this.nkaType = nkaType;
        this.distr = distr;
        this.user = user;
    }

    public NkaType getNkaType() {
        return nkaType;
    }

    public void setNkaType(NkaType nkaType) {
        this.nkaType = nkaType;
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

        NkaResp nkaResp = (NkaResp) o;

        if (!nkaType.equals(nkaResp.nkaType)) return false;
        return distr.equals(nkaResp.distr);
    }

    @Override
    public int hashCode() {
        int result = nkaType.hashCode();
        result = 31 * result + distr.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NkaResp{" +
                "nkaType=" + nkaType +
                ", distr=" + distr +
                ", user=" + user +
                '}';
    }

    @Override
    public int compareTo(NkaResp o) {
        if (this.getNkaType().getId() > o.getNkaType().getId())
            return 1;
        else if (this.getNkaType().getId() < o.getNkaType().getId())
            return -1;

        int r = (this.getDistr().getRegion().getName().compareTo(o.getDistr().getRegion().getName()));
        if (r != 0)
            return r;

        return this.getDistr().getName().compareTo(o.getDistr().getName());
    }
}
