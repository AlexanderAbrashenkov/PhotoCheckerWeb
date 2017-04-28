package com.photochecker.dao;

import com.photochecker.model.Region;
import com.photochecker.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface RegionDAO {
    public boolean create(Region region);
    public Region find(int id);
    public List<Region> findAll();
    public List<Region> findAllByDates(LocalDate startDate, LocalDate endDate);
}
