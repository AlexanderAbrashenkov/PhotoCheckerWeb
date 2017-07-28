package com.photochecker.model.common;

/**
 * Created by market6 on 25.07.2017.
 */
public class FormatType {
    private int id;
    private String name;

    public FormatType() {
    }

    public FormatType(int id, String name) {
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

        FormatType formatType = (FormatType) o;

        return name.equals(formatType.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
