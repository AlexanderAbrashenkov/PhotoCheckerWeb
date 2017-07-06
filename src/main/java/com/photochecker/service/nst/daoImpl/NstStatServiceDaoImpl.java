package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstFormatDao;
import com.photochecker.dao.nst.NstOblDao;
import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.dao.nst.NstStatDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstResp;
import com.photochecker.model.nst.NstStat;
import com.photochecker.service.nst.NstStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NstStatServiceDaoImpl implements NstStatService {

    @Autowired
    private NstStatDao nstStatDao;
    @Autowired
    private NstRespDao nstRespDao;
    @Autowired
    private NstFormatDao nstFormatDao;
    @Autowired
    private NstOblDao nstOblDao;

    @Override
    public NstStat getStat(User user, int targetFormatId, int targetOblId, LocalDate dateFrom, LocalDate dateTo) {

        NstStat nstStat = new NstStat();

        LocalDate today = LocalDate.now();

        if (user.getRole() > 1) {
            List<Integer> nstFormatList = nstFormatDao.findAll().stream()
                    .map(nstFormat -> nstFormat.getId())
                    .collect(Collectors.toList());

            List<Integer> nstOblList = nstOblDao.findAll().stream()
                    .map(nstObl -> nstObl.getId())
                    .collect(Collectors.toList());

            for (int formatId : nstFormatList) {
                for (int nstOblId : nstOblList) {
                    int ttOblCount = nstStatDao.getTtCount(formatId, nstOblId, dateFrom, dateTo, 4);
                    List<LocalDateTime> savedDatesObl = nstStatDao.getSavedDates(formatId, nstOblId, dateFrom, dateTo);
                    int savedTodayObl = (int) savedDatesObl.stream()
                            .filter(localDateTime -> localDateTime.toLocalDate().equals(today))
                            .count();

                    nstStat.setTotalCount(nstStat.getTotalCount() + ttOblCount);
                    nstStat.setTotalChecked(nstStat.getTotalChecked() + savedDatesObl.size());
                    nstStat.setTotalCheckedToday(nstStat.getTotalCheckedToday() + savedTodayObl);

                    if (targetFormatId == formatId && targetOblId == nstOblId) {
                        nstStat.setOblCount(ttOblCount);
                        nstStat.setOblChecked(savedDatesObl.size());
                        nstStat.setOblCheckedToday(savedTodayObl);
                    }
                }
            }
            return nstStat;
        }

        List<NstResp> nstRespList = nstRespDao.findAllByUser(user);

        List<Integer> allowedFormats = nstRespList.stream()
                .map(nstResp -> nstResp.getNstFormat().getId())
                .distinct()
                .collect(Collectors.toList());

        if (allowedFormats.size() > 0) {
            for (int formatId : allowedFormats) {
                List<Integer> allowedNstObl = nstRespList.stream()
                        .filter(nstResp -> nstResp.getNstFormat().getId() == formatId)
                        .map(nstResp -> nstResp.getNstObl().getId())
                        .distinct()
                        .collect(Collectors.toList());

                if (allowedNstObl.size() == 0) continue;

                for (int nstOblId : allowedNstObl) {
                    int ttOblCount = nstStatDao.getTtCount(formatId, nstOblId, dateFrom, dateTo, 4);
                    List<LocalDateTime> savedDatesObl = nstStatDao.getSavedDates(formatId, nstOblId, dateFrom, dateTo);
                    int savedTodayObl = (int) savedDatesObl.stream()
                            .filter(localDateTime -> localDateTime.toLocalDate().equals(today))
                            .count();

                    nstStat.setTotalCount(nstStat.getTotalCount() + ttOblCount);
                    nstStat.setTotalChecked(nstStat.getTotalChecked() + savedDatesObl.size());
                    nstStat.setTotalCheckedToday(nstStat.getTotalCheckedToday() + savedTodayObl);

                    if (targetFormatId == formatId && targetOblId == nstOblId) {
                        nstStat.setOblCount(ttOblCount);
                        nstStat.setOblChecked(savedDatesObl.size());
                        nstStat.setOblCheckedToday(savedTodayObl);
                    }
                }
            }
        }

        return nstStat;
    }
}
