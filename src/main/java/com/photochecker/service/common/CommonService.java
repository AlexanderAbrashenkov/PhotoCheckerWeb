package com.photochecker.service.common;

import java.time.DayOfWeek;
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

    public LocalDate getInitialStartDateWeek() {
        LocalDate result = LocalDate.now().minusDays(1);
        while (!result.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            result = result.minusDays(1);
        }
        return result;
    }

    public LocalDate getInitialEndDateWeek() {
        LocalDate result = LocalDate.now().minusDays(1);
        while (!result.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            result = result.plusDays(1);
        }
        return result;
    }

    public LocalDate getInitialStartDateNst() {
        LocalDate result = LocalDate.now().minusDays(7);
        while (!result.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            result = result.minusDays(1);
        }
        return result;
    }

    public LocalDate getInitialEndDateNst() {
        LocalDate result = LocalDate.now().minusDays(1);
        while (!result.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            result = result.minusDays(1);
        }
        return result;
    }
}
