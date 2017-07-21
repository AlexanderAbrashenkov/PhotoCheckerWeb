package com.photochecker.model.nst;

import javax.persistence.*;

@Entity
@Table(name = "nst_client_card")
public class NstClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "obl_id")
    private NstObl nstObl;

    @ManyToOne
    @JoinColumn(name = "format_id")
    private NstFormat nstFormat;

    private int checked;

    public NstClientCard() {
    }

    public NstClientCard(int id, String name, NstObl nstObl, NstFormat nstFormat, int checked) {
        this.id = id;
        this.name = name;
        this.nstObl = nstObl;
        this.nstFormat = nstFormat;
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

    public NstFormat getNstFormat() {
        return nstFormat;
    }

    public void setNstFormat(NstFormat nstFormat) {
        this.nstFormat = nstFormat;
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

        if (!name.equals(that.name)) return false;
        if (!nstObl.equals(that.nstObl)) return false;
        return nstFormat.equals(that.nstFormat);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + nstObl.hashCode();
        result = 31 * result + nstFormat.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NstClientCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nstObl=" + nstObl +
                ", nstFormat=" + nstFormat +
                ", checked=" + checked +
                '}';
    }
}
