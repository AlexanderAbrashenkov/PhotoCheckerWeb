package com.photochecker.service.common;

import com.photochecker.model.common.Distr;
import com.photochecker.model.common.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface DistrService {


    List<Distr> getDistrs(User user, int regionId, LocalDate startDate, LocalDate endDate, int repTypeInd);

    List<Distr> getDistrs(User user, int regionId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId);
}
