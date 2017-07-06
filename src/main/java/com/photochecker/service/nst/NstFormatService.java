package com.photochecker.service.nst;

import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 05.07.2017.
 */
public interface NstFormatService {

    List<NstFormat> getNstFormats(User user, LocalDate startDate, LocalDate endDate, int repTypeIndex);
}
