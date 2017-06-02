package com.photochecker.service.common;

import com.photochecker.model.common.Responsibility;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface ResponsibilitiesService {
    public List<Responsibility> getAllResponsibilities();
    public boolean writeResponsibilities(List<Responsibility> respList);
}
