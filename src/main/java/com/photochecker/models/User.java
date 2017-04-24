package com.photochecker.models;

import java.util.List;

/**
 * Created by market6 on 05.04.2017.
 */

public class User {
    private String userLogin;
    private String userName;
    private int role;
    private List<Integer> reportTypeList;

    public User() {
    }

    public User(String userLogin, String userName, int role, List<Integer> reportTypeList) {
        this.userLogin = userLogin;
        this.userName = userName;
        this.role = role;
        this.reportTypeList = reportTypeList;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<Integer> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<Integer> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userLogin='" + userLogin + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", reportTypeList=" + reportTypeList +
                '}';
    }
}
