package com.photochecker.model.nka;

public class NkaReportItem {
    private int nkaId;
    private String nkaName;
    private String type;
    private int clientId;
    private String clientName;
    private String clientAddress;

    private int mzPlan;
    private int kPlan;
    private int sPlan;

    private int mzDp;
    private int mzBb;
    private int mzMr;
    private String mzComment;

    private int kDp;
    private int kBb;
    private int kMr;
    private String kComment;

    private int sDp;
    private int sBb;
    private int sMr;
    private String sComment;

    private int mzDouble;
    private int kDouble;
    private int sDouble;

    private int mzDmAPlan;
    private int kDmAPlan;
    private int sDmAPlan;

    private int mzDmA;
    private int kDmA;
    private int sDmA;

    private int mzDmNa;
    private int kDmNa;
    private int sDmNa;

    private String lastPhotoDate;

    public NkaReportItem() {
    }

    public NkaReportItem(int nkaId, String nkaName, String type, int clientId, String clientName, String clientAddress,
                         int mzPlan, int kPlan, int sPlan,
                         int mzDp, int mzBb, int mzMr, String mzComment,
                         int kDp, int kBb, int kMr, String kComment,
                         int sDp, int sBb, int sMr, String sComment,
                         int mzDouble, int kDouble, int sDouble,
                         int mzDmAPlan, int kDmAPlan, int sDmAPlan,
                         int mzDmA, int kDmA, int sDmA,
                         int mzDmNa, int kDmNa, int sDmNa, String lastPhotoDate) {
        this.nkaId = nkaId;
        this.nkaName = nkaName;
        this.type = type;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.mzPlan = mzPlan;
        this.kPlan = kPlan;
        this.sPlan = sPlan;
        this.mzDp = mzDp;
        this.mzBb = mzBb;
        this.mzMr = mzMr;
        this.mzComment = mzComment;
        this.kDp = kDp;
        this.kBb = kBb;
        this.kMr = kMr;
        this.kComment = kComment;
        this.sDp = sDp;
        this.sBb = sBb;
        this.sMr = sMr;
        this.sComment = sComment;
        this.mzDouble = mzDouble;
        this.kDouble = kDouble;
        this.sDouble = sDouble;
        this.mzDmAPlan = mzDmAPlan;
        this.kDmAPlan = kDmAPlan;
        this.sDmAPlan = sDmAPlan;
        this.mzDmA = mzDmA;
        this.kDmA = kDmA;
        this.sDmA = sDmA;
        this.mzDmNa = mzDmNa;
        this.kDmNa = kDmNa;
        this.sDmNa = sDmNa;
        this.lastPhotoDate = lastPhotoDate;
    }

    public int getNkaId() {
        return nkaId;
    }

    public void setNkaId(int nkaId) {
        this.nkaId = nkaId;
    }

    public String getNkaName() {
        return nkaName;
    }

    public void setNkaName(String nkaName) {
        this.nkaName = nkaName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public int getMzPlan() {
        return mzPlan;
    }

    public void setMzPlan(int mzPlan) {
        this.mzPlan = mzPlan;
    }

    public int getkPlan() {
        return kPlan;
    }

    public void setkPlan(int kPlan) {
        this.kPlan = kPlan;
    }

    public int getsPlan() {
        return sPlan;
    }

    public void setsPlan(int sPlan) {
        this.sPlan = sPlan;
    }

    public int getMzDp() {
        return mzDp;
    }

    public void setMzDp(int mzDp) {
        this.mzDp = mzDp;
    }

    public int getMzBb() {
        return mzBb;
    }

    public void setMzBb(int mzBb) {
        this.mzBb = mzBb;
    }

    public int getMzMr() {
        return mzMr;
    }

    public void setMzMr(int mzMr) {
        this.mzMr = mzMr;
    }

    public String getMzComment() {
        return mzComment;
    }

    public void setMzComment(String mzComment) {
        this.mzComment = mzComment;
    }

    public int getkDp() {
        return kDp;
    }

    public void setkDp(int kDp) {
        this.kDp = kDp;
    }

    public int getkBb() {
        return kBb;
    }

    public void setkBb(int kBb) {
        this.kBb = kBb;
    }

    public int getkMr() {
        return kMr;
    }

    public void setkMr(int kMr) {
        this.kMr = kMr;
    }

    public String getkComment() {
        return kComment;
    }

    public void setkComment(String kComment) {
        this.kComment = kComment;
    }

    public int getsDp() {
        return sDp;
    }

    public void setsDp(int sDp) {
        this.sDp = sDp;
    }

    public int getsBb() {
        return sBb;
    }

    public void setsBb(int sBb) {
        this.sBb = sBb;
    }

    public int getsMr() {
        return sMr;
    }

    public void setsMr(int sMr) {
        this.sMr = sMr;
    }

    public String getsComment() {
        return sComment;
    }

    public void setsComment(String sComment) {
        this.sComment = sComment;
    }

    public int getMzDouble() {
        return mzDouble;
    }

    public void setMzDouble(int mzDouble) {
        this.mzDouble = mzDouble;
    }

    public int getkDouble() {
        return kDouble;
    }

    public void setkDouble(int kDouble) {
        this.kDouble = kDouble;
    }

    public int getsDouble() {
        return sDouble;
    }

    public void setsDouble(int sDouble) {
        this.sDouble = sDouble;
    }

    public int getMzDmAPlan() {
        return mzDmAPlan;
    }

    public void setMzDmAPlan(int mzDmAPlan) {
        this.mzDmAPlan = mzDmAPlan;
    }

    public int getkDmAPlan() {
        return kDmAPlan;
    }

    public void setkDmAPlan(int kDmAPlan) {
        this.kDmAPlan = kDmAPlan;
    }

    public int getsDmAPlan() {
        return sDmAPlan;
    }

    public void setsDmAPlan(int sDmAPlan) {
        this.sDmAPlan = sDmAPlan;
    }

    public int getMzDmA() {
        return mzDmA;
    }

    public void setMzDmA(int mzDmA) {
        this.mzDmA = mzDmA;
    }

    public int getkDmA() {
        return kDmA;
    }

    public void setkDmA(int kDmA) {
        this.kDmA = kDmA;
    }

    public int getsDmA() {
        return sDmA;
    }

    public void setsDmA(int sDmA) {
        this.sDmA = sDmA;
    }

    public int getMzDmNa() {
        return mzDmNa;
    }

    public void setMzDmNa(int mzDmNa) {
        this.mzDmNa = mzDmNa;
    }

    public int getkDmNa() {
        return kDmNa;
    }

    public void setkDmNa(int kDmNa) {
        this.kDmNa = kDmNa;
    }

    public int getsDmNa() {
        return sDmNa;
    }

    public void setsDmNa(int sDmNa) {
        this.sDmNa = sDmNa;
    }

    public String getLastPhotoDate() {
        return lastPhotoDate;
    }

    public void setLastPhotoDate(String lastPhotoDate) {
        this.lastPhotoDate = lastPhotoDate;
    }
}
