package com.photochecker.models;

/**
 * Created by market6 on 27.03.2017.
 */
public class ClientCard {
    private int clientId;
    private String clientName;
    private String clientAddress;
    private String clientType;
    private boolean checked;

    public ClientCard() {
    }

    public ClientCard(int clientId, String clientName, String clientAddress, String clientType, boolean checked) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientType = clientType;
        this.checked = checked;
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

    @Override
    public String toString() {
        return "ClientCard{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientType='" + clientType + '\'' +
                ", checked=" + checked +
                '}';
    }
}
