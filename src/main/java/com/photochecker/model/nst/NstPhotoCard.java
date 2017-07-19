package com.photochecker.model.nst;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NstPhotoCard {
    private int id;
    private int clientId;
    private String url;
    private LocalDateTime date;

    public NstPhotoCard() {
    }

    public NstPhotoCard(int clientId, String url, LocalDateTime date) {
        this.clientId = clientId;
        this.url = url;
        this.date = date;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NstPhotoCard that = (NstPhotoCard) o;

        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "NstPhotoCard{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", url='" + url + '\'' +
                ", date=" + date +
                '}';
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return this.date.format(formatter);
    }
}
