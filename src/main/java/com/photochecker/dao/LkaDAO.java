package com.photochecker.dao;

import com.photochecker.model.Distr;
import com.photochecker.model.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface LkaDAO {
    public boolean create(Lka lka);
    public Lka find(int id);
    public List<Lka> findAll();
    public List<Lka> findAllByDatesAndDistr(Distr distr, LocalDate startDate, LocalDate endDate);
}
