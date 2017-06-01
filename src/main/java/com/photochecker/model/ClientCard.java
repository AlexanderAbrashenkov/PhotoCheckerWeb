package com.photochecker.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by market6 on 27.03.2017.
 */
public class ClientCard {
    private int clientId;
    private String clientName;
    private String clientAddress;
    private String clientType;
    private boolean checked;
    private Distr distr;
    private String obl;
    private int channelId;
    private Lka lka;
    private int nkaType;

    public ClientCard() {
    }

    public ClientCard(int clientId, String clientName, String clientAddress, String clientType,
                      boolean checked, Distr distr, String obl, int channelId, Lka lka, int nkaType) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientType = clientType;
        this.checked = checked;
        this.distr = distr;
        this.obl = obl;
        this.channelId = channelId;
        this.lka = lka;
        this.nkaType = nkaType;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Distr getDistr() {
        return distr;
    }

    public void setDistr(Distr distr) {
        this.distr = distr;
    }

    public String getObl() {
        return obl;
    }

    public void setObl(String obl) {
        this.obl = obl;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public Lka getLka() {
        return lka;
    }

    public void setLka(Lka lka) {
        this.lka = lka;
    }

    public int getNkaType() {
        return nkaType;
    }

    public void setNkaType(int nkaType) {
        this.nkaType = nkaType;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientType='" + clientType + '\'' +
                ", checked=" + checked +
                ", distr=" + distr +
                ", obl='" + obl + '\'' +
                ", channelId=" + channelId +
                ", lka=" + lka +
                ", nkaType=" + nkaType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientCard that = (ClientCard) o;

        return clientId == that.clientId;
    }

    @Override
    public int hashCode() {
        return clientId;
    }
}
