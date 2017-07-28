package com.photochecker.model.common;

import com.google.common.base.MoreObjects;

/**
 * Created by market6 on 27.03.2017.
 */
public class ClientCard {
    private int clientId;
    private String clientName;
    private String clientAddress;
    private FormatType formatType;
    private int checked;
    private Distr distr;
    private String obl;
    private int channelId;
    private Lka lka;
    private int nkaType;

    public ClientCard() {
    }

    public ClientCard(int clientId, String clientName, String clientAddress, FormatType formatType,
                      int checked, Distr distr, String obl, int channelId, Lka lka, int nkaType) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.formatType = formatType;
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

    public FormatType getFormatType() {
        return formatType;
    }

    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
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
        return MoreObjects.toStringHelper(this)
                .add("clientId", clientId)
                .add("clientName", clientName)
                .add("clientAddress", clientAddress)
                .add("formatType", formatType)
                .add("checked", checked)
                .add("distr", distr)
                .add("obl", obl)
                .add("channelId", channelId)
                .add("lka", lka)
                .add("nkaType", nkaType)
                .toString();
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
