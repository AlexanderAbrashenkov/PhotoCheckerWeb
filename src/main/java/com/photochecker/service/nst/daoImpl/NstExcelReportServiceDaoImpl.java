package com.photochecker.service.nst.daoImpl;


import com.photochecker.dao.nst.NstReportItemDao;
import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstReportItem;
import com.photochecker.model.nst.NstResp;
import com.photochecker.service.nst.NstExcelReportService;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NstExcelReportServiceDaoImpl implements NstExcelReportService {

    @Autowired
    private NstReportItemDao nstReportItemDao;
    @Autowired
    private NstRespDao nstRespDao;
    @Autowired
    private ServletContext servletContext;

    @Override
    public void getExcelReportItems(OutputStream out, LocalDate dateFrom, LocalDate dateTo, User user) {

        Set<NstReportItem> nstReportItemList = new TreeSet<>();

        if (user.getRole() > 1) {
            nstReportItemList = nstReportItemDao.findAllByDatesAndRepType(dateFrom, dateTo, 4);
        }

        if (user.getRole() == 1) {

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
                        nstReportItemList.addAll(nstReportItemDao.findAllByUserParams(user, formatId, nstOblId, dateFrom, dateTo, 4));
                    }
                }
            }
        }

        int i = 1;

        for (NstReportItem nstReportItem : nstReportItemList) {
            nstReportItem.setIndex(i);
            i++;
        }

        File file = new File(servletContext.getRealPath("/resources/excelTemplates/nst_template.xlsx"));

        try (InputStream is = new FileInputStream(file)) {

            Context context = PoiTransformer.createInitialContext();
            context.putVar("nstReportItemList", nstReportItemList);
            JxlsHelper.getInstance().processTemplate(is, out, context);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
