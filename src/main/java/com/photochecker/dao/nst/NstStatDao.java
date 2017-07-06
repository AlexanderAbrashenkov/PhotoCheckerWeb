package com.photochecker.dao.nst;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by market6 on 06.07.2017.
 */
public interface NstStatDao {
    public int getTtCount (int formatId, int oblId, LocalDate startDate, LocalDate endDate, int repType);
    public List<LocalDateTime> getSavedDates (int formatId, int oblId, LocalDate startDate, LocalDate endDate);
}
