package com.photochecker.model.nst;

import javax.persistence.*;

@Entity
@Table(name = "nst_format")
public class NstFormat {
    @Id
    @Column
    private int id;

    @Column
    private String name;

    public NstFormat() {
    }

    public NstFormat(int id, String name) {
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

        NstFormat nstFormat = (NstFormat) o;

        return id == nstFormat.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "NstFormat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
