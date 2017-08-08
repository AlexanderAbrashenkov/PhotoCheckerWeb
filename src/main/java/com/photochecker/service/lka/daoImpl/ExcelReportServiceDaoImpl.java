package com.photochecker.service.lka.daoImpl;

import com.photochecker.apache_poi.ApachePoi;
import com.photochecker.apache_poi.ApachePoiManager;
import com.photochecker.dao.common.ResponsibilityDao;
import com.photochecker.dao.lka.LkaReportItemDao;
import com.photochecker.model.common.Responsibility;
import com.photochecker.model.common.User;
import com.photochecker.model.lka.LkaReportItem;
import com.photochecker.service.lka.ExcelReportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExcelReportServiceDaoImpl implements ExcelReportService {

    @Autowired
    private LkaReportItemDao lkaReportItemDao;
    @Autowired
    private ResponsibilityDao responsibilityDao;
    @Autowired
    private ServletContext servletContext;

    @Override
    public void getExcelReport(OutputStream out, LocalDate dateFrom, LocalDate dateTo, User user) {
        List<LkaReportItem> lkaReportItemList = lkaReportItemDao.findAllByDatesAndRepType(dateFrom, dateTo, 5);

        if (user.getRole() == 1) {
            List<Responsibility> responsibilityList = responsibilityDao.findAllByUser(user);
            List<String> allowedDistrNames = responsibilityList.stream()
                    .filter(resp -> resp.getReportType().getId() == 5)
                    .map(resp -> resp.getDistr().getName())
                    .distinct()
                    .collect(Collectors.toList());
            lkaReportItemList.removeIf(lkaReportItem -> !allowedDistrNames.contains(lkaReportItem.getDistr()));
        }

        for (int i = 0; i < lkaReportItemList.size(); i++) {
            lkaReportItemList.get(i).setIndex(i + 1);
        }



        File file = new File(servletContext.getRealPath("/resources/excelTemplates/lka_template.xlsx"));

        try (InputStream is = new FileInputStream(file)) {

            Context context = PoiTransformer.createInitialContext();
            context.putVar("lkaReportItemList", lkaReportItemList);
            JxlsHelper.getInstance().processTemplate(is, out, context);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
