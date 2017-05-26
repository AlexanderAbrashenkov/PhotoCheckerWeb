package com.photochecker.service.common;

import com.photochecker.model.Region;
import com.photochecker.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface RegionService {

    public List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate, int repTypeInd);
}
