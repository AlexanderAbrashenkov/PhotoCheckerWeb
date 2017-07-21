package com.photochecker.model.nst;

import javax.persistence.*;

@Entity
@Table(name = "nst_obl")
public class NstObl {
    @Id
    @Column
    private int id;

    @Column
    private String name;

    public NstObl() {
    }

    public NstObl(int id, String name) {
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

        NstObl nstObl = (NstObl) o;

        return name.toLowerCase().equals(nstObl.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "NstObl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
