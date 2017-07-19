package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstReportItem;

import java.time.LocalDate;
import java.util.Set;

/**
 * Created by market6 on 21.06.2017.
 */
public interface NstReportItemDao extends GenericDao<NstReportItem> {
    Set<NstReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType);

    Set<NstReportItem> findAllByUserParams(User user, int formatId, int nstOblId, LocalDate startDate, LocalDate endDate, int repTypeInd);
}
