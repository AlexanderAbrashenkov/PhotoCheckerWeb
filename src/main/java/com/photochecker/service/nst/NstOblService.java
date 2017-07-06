package com.photochecker.service.nst;

import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstObl;

import java.time.LocalDate;
import java.util.List;

public interface NstOblService {
    List<NstObl> getNstObls(User user, LocalDate startDate, LocalDate endDate, int formatId, int repTypeIndex);
}
