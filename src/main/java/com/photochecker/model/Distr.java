package com.photochecker.model;

/**
 * Created by market6 on 27.04.2017.
 */
public class Distr {
    private int id;
    private String name;
    private Region region;

    public Distr() {
    }

    public Distr(int id, String name, Region region) {
        this.id = id;
        this.name = name;
        this.region = region;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Distr distr = (Distr) o;

        return id == distr.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
