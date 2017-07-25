package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.dao.nst.NstStatDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstResp;
import com.photochecker.model.nst.NstStat;
import com.photochecker.service.nst.NstStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NstStatServiceDaoImpl implements NstStatService {

    @Autowired
    private NstStatDao nstStatDao;
    @Autowired
    private NstRespDao nstRespDao;

    @Override
    public NstStat getStat(User user, int targetFormatId, int targetOblId, LocalDate dateFrom, LocalDate dateTo) {

        NstStat nstStat = new NstStat();

        if (user.getRole() > 1) {
            nstStat = nstStatDao.getTotalStat(dateFrom, dateTo);

            setOblStat(targetFormatId, targetOblId, dateFrom, dateTo, nstStat);

            return nstStat;
        }

        List<NstResp> nstRespList = nstRespDao.findAllByUser(user);

        List<Integer> allowedFormats = nstRespList.stream()
                .map(nstResp -> nstResp.getNstFormat().getId())
                .distinct()
                .collect(Collectors.toList());

        setOblStat(targetFormatId, targetOblId, dateFrom, dateTo, nstStat);
        List<NstStat> nstStatList = nstStatDao.getOblListStat(dateFrom, dateTo);

        if (allowedFormats.size() > 0) {
            for (int formatId : allowedFormats) {
                List<Integer> allowedNstObl = nstRespList.stream()
                        .filter(nstResp -> nstResp.getNstFormat().getId() == formatId)
                        .map(nstResp -> nstResp.getNstObl().getId())
                        .distinct()
                        .collect(Collectors.toList());

                if (allowedNstObl.size() == 0) continue;

                for (int nstOblId : allowedNstObl) {
                    NstStat oblStat = null;
                    List<NstStat> nstStats = nstStatList.stream()
                            .filter(nstStat1 -> nstStat1.getFormatId() == formatId)
                            .filter(nstStat1 -> nstStat1.getOblId() == nstOblId)
                            .collect(Collectors.toList());

                    if (nstStats.size() > 0) {
                        oblStat = nstStats.get(0);
                        nstStat.setTotalCount(nstStat.getTotalCount() + oblStat.getOblCount());
                        nstStat.setTotalChecked(nstStat.getTotalChecked() + oblStat.getOblChecked());
                        nstStat.setTotalCheckedToday(nstStat.getTotalCheckedToday() + oblStat.getOblCheckedToday());
                    }
                }
            }
        }

        return nstStat;
    }

    private void setOblStat(int targetFormatId, int targetOblId, LocalDate dateFrom, LocalDate dateTo, NstStat nstStat) {
        NstStat oblStat = nstStatDao.getOblStat(targetFormatId, targetOblId, dateFrom, dateTo);

        nstStat.setOblCount(oblStat.getOblCount());
        nstStat.setOblChecked(oblStat.getOblChecked());
        nstStat.setOblCheckedToday(oblStat.getOblCheckedToday());
    }

    @Override
    public int cleanCheckedToday() {
        return nstStatDao.clearCheckedToday();
    }
}
