package com.photochecker.model.mlka;

/**
 * Created by market6 on 31.05.2017.
 */
public class NkaType {
    private int id;
    private String name;

    public NkaType() {
    }

    public NkaType(int id, String name) {
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

        NkaType nkaType = (NkaType) o;

        return id == nkaType.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "NkaType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
