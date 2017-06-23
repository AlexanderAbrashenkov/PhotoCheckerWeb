package com.photochecker.model.nst;

import com.photochecker.model.common.User;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstResp implements Comparable<NstResp> {
    private NstObl nstObl;
    private User user;

    public NstResp(NstObl nstObl, User user) {
        this.nstObl = nstObl;
        this.user = user;
    }

    public NstResp() {
    }

    public NstObl getNstObl() {
        return nstObl;
    }

    public void setNstObl(NstObl nstObl) {
        this.nstObl = nstObl;
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

        NstResp nstResp = (NstResp) o;

        return nstObl.equals(nstResp.nstObl);
    }

    @Override
    public int hashCode() {
        return nstObl.hashCode();
    }

    @Override
    public int compareTo(NstResp o) {
        return nstObl.getName().compareTo(o.getNstObl().getName());
    }
}
