package com.photochecker.model.nst;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstObl {
    private int id;
    private String name;
    private boolean checked;

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
