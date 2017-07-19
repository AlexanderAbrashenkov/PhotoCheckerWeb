package com.photochecker.service.nst;

import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstStat;

import java.time.LocalDate;

/**
 * Created by market6 on 06.07.2017.
 */
public interface NstStatService {

    NstStat getStat(User user, int targetFormatId, int targetOblId, LocalDate dateFrom, LocalDate dateTo);

    int cleanCheckedToday();
}
