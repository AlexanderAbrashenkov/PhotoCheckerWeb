package com.photochecker.service.common;

import com.photochecker.model.common.Region;
import com.photochecker.model.common.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface RegionService {

    List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate, int repTypeInd);

    List<Region> getRegions(User user, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId);
}
