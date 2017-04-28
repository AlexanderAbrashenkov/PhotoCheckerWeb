package com.photochecker.dao;

import com.photochecker.model.ReportType;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ReportTypeDAO {
    public ReportType find(int id);
    public List<ReportType> findAll();
}
