package com.photochecker.dao;

import com.photochecker.model.Distr;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface DistrDAO {
    public boolean create(Distr distr);
    public Distr find(int id);
    public List<Distr> findAll();
    public List<Distr> findAllByDates(LocalDate startDate, LocalDate endDate);
}
