package com.photochecker.service.mlka;

import com.photochecker.model.common.User;
import com.photochecker.model.mlka.NkaType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 31.05.2017.
 */
public interface NkaTypeService {
    List<NkaType> getNkaTypes(User user, LocalDate startDate, LocalDate endDate, int repTypeInd);
}
