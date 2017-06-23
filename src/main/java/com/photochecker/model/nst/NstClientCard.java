package com.photochecker.model.nst;

/**
 * Created by market6 on 20.06.2017.
 */
public class NstClientCard {
    private int id;
    private String name;
    private NstObl nstObl;
    private int checked;

    public NstClientCard() {
    }

    public NstClientCard(int id, String name, NstObl nstObl, int checked) {
        this.id = id;
        this.name = name;
        this.nstObl = nstObl;
        this.checked = checked;
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

    public NstObl getNstObl() {
        return nstObl;
    }

    public void setNstObl(NstObl nstObl) {
        this.nstObl = nstObl;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NstClientCard that = (NstClientCard) o;

        if (!name.toLowerCase().equals(that.name.toLowerCase())) return false;
        return nstObl.equals(that.nstObl);
    }

    @Override
    public int hashCode() {
        int result = name.toLowerCase().hashCode();
        result = 31 * result + nstObl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NstClientCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nstObl=" + nstObl +
                ", checked=" + checked +
                '}';
    }
}
