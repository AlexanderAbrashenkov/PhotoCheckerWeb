package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstReportItem;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Created by market6 on 21.06.2017.
 */
public interface NstReportItemDao extends GenericDao<NstReportItem> {
    List<NstReportItem> findAllByDatesAndRepType(LocalDate startDate, LocalDate endDate, int repType);

    List<NstReportItem> findAllByUserParams(User user, int formatId, int nstOblId, LocalDate startDate, LocalDate endDate, int repTypeInd);
}
