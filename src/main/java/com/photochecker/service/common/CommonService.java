package com.photochecker.service.common;

import java.time.LocalDate;

/**
 * Created by market6 on 17.05.2017.
 */
public abstract class CommonService {

    public LocalDate getInitialStartDate() {
        LocalDate startDate = LocalDate.now().minusDays(2);
        return startDate;
    }

    public LocalDate getInitialEndDate() {
        LocalDate endDate = LocalDate.now().minusDays(2);
        return endDate;
    }
}
