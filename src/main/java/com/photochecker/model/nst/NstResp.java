package com.photochecker.model.nst;

import com.photochecker.model.common.User;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstResp implements Comparable<NstResp> {
    private NstFormat nstFormat;
    private NstObl nstObl;
    private User user;

    public NstResp(NstFormat nstFormat, NstObl nstObl, User user) {
        this.nstFormat = nstFormat;
        this.nstObl = nstObl;
        this.user = user;
    }

    public NstResp() {
    }

    public NstFormat getNstFormat() {
        return nstFormat;
    }

    public void setNstFormat(NstFormat nstFormat) {
        this.nstFormat = nstFormat;
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

        if (!nstFormat.equals(nstResp.nstFormat)) return false;
        return nstObl.equals(nstResp.nstObl);
    }

    @Override
    public int hashCode() {
        int result = nstFormat.hashCode();
        result = 31 * result + nstObl.hashCode();
        return result;
    }

    @Override
    public int compareTo(NstResp o) {
        if (nstFormat.getId() - o.getNstFormat().getId() != 0) {
            return nstFormat.getId() - o.getNstFormat().getId();
        }
        return nstObl.getName().compareTo(o.getNstObl().getName());
    }
}
