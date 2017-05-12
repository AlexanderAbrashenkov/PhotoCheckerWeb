package com.photochecker.model;

import java.util.List;

/**
 * Created by market6 on 05.04.2017.
 */

public class User {
    private int id;
    private String userLogin;
    private String userName;
    private int role;
    private List<ReportType> reportTypeList;

    public User() {
    }

    public User(int id, String userLogin, String userName, int role, List<ReportType> reportTypeList) {
        this.id = id;
        this.userLogin = userLogin;
        this.userName = userName;
        this.role = role;
        this.reportTypeList = reportTypeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ReportType> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<ReportType> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userLogin='" + userLogin + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", reportTypeList=" + reportTypeList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
