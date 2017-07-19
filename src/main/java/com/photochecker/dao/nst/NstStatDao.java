package com.photochecker.dao.nst;

import com.photochecker.model.nst.NstStat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by market6 on 06.07.2017.
 */
public interface NstStatDao {

    NstStat getTotalStat(LocalDate startDate, LocalDate endDate);

    NstStat getOblStat(int formatId, int oblId, LocalDate startDate, LocalDate endDate);

    int clearCheckedToday();

    void fillUpWeekStat(LocalDate startDate, LocalDate endDate);

    void increaseChecked(int formatId, int oblId, LocalDate startDate, LocalDate endDate);

    void increaseCheckedToday(int formatId, int oblId, LocalDate dateFrom, LocalDate dateTo);
}
