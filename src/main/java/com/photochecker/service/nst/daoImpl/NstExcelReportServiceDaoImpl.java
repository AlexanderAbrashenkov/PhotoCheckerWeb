package com.photochecker.service.nst.daoImpl;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.dao.nst.NstReportItemDao;
import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstReportItem;
import com.photochecker.model.nst.NstResp;
import com.photochecker.service.nst.NstExcelReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NstExcelReportServiceDaoImpl implements NstExcelReportService {

    @Autowired
    private NstReportItemDao nstReportItemDao;
    @Autowired
    private NstRespDao nstRespDao;

    @Override
    public Workbook getExcelReport(Workbook workbook, LocalDate dateFrom, LocalDate dateTo, User user) {

        List<NstReportItem> nstReportItemList = new ArrayList<>();

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

        ApachePoiManager.createApachePoi(4);
        ApachePoi apachePoi = ApachePoiManager.getInstance();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        apachePoi.createReportFile(workbook, dateFrom.format(formatter), dateTo.format(formatter));

        String sheetName = "ММ";

        apachePoi.createConcreteSheet(sheetName, null);
        for (NstReportItem nstReportItem : nstReportItemList) {
            apachePoi.writeOneTtToConcreteSheet(new ArrayList(Arrays.asList(nstReportItem)));
        }

        apachePoi.calcSumRowConcreteSheet("NST");

        return workbook;
    }
}