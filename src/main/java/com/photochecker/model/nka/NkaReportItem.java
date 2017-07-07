package com.photochecker.model.nka;

/**
 * Created by market6 on 07.07.2017.
 */
public class NkaReportItem {
    private int nkaId;
    private String nkaName;
    private String type;
    private int clientId;
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

    private int dmA;
    private int dmNa;

    public NkaReportItem() {
    }

    public NkaReportItem(int nkaId, String nkaName, String type, int clientId, String clientAddress,
                         int mzPlan, int kPlan, int sPlan,
                         int mzDp, int mzBb, int mzMr, String mzComment,
                         int kDp, int kBb, int kMr, String kComment,
                         int sDp, int sBb, int sMr, String sComment,
                         int mzDouble, int kDouble, int sDouble,
                         int dmA, int dmNa) {
        this.nkaId = nkaId;
        this.nkaName = nkaName;
        this.type = type;
        this.clientId = clientId;
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
        this.dmA = dmA;
        this.dmNa = dmNa;
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

    public int getDmA() {
        return dmA;
    }

    public void setDmA(int dmA) {
        this.dmA = dmA;
    }

    public int getDmNa() {
        return dmNa;
    }

    public void setDmNa(int dmNa) {
        this.dmNa = dmNa;
    }
}
