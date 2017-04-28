package com.photochecker.model;

/**
 * Created by market6 on 27.04.2017.
 */
public class ReportType {
    private int id;
    private String name;

    public ReportType() {
    }

    public ReportType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportType that = (ReportType) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
