package com.photochecker.model;

/**
 * Created by market6 on 27.04.2017.
 */
public class Lka {
    private int id;
    private String name;

    public Lka() {
    }

    public Lka(int id, String name) {
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

        Lka lka = (Lka) o;

        return id == lka.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Lka{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
