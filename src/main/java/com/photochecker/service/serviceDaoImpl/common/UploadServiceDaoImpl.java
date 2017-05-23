package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.common.*;
import com.photochecker.model.*;
import com.photochecker.service.common.UploadService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by market6 on 23.05.2017.
 */
public class UploadServiceDaoImpl implements UploadService {
    @Autowired
    private RegionDao regionDao;
    @Autowired
    private DistrDao distrDao;
    @Autowired
    private LkaDao lkaDao;
    @Autowired
    private ReportTypeDao reportTypeDao;
    @Autowired
    private ResponsibilityDao responsibilityDao;
    @Autowired
    private ClientCardDao clientCardDao;
    @Autowired
    private PhotoCardDao photoCardDao;

    @Override
    public int uploadDatas(BufferedReader reader, String date) {
        LocalDate dateAdd = LocalDate.parse(date);
        int recordCounter = 0;

        try {

            while (reader.ready()) {
                String record = reader.readLine();

                //todo: для разных каналов разделить
                if (record.startsWith("--")) continue;

                String[] recordParts = record.split("; ");

                if (recordParts.length < 16) continue;


                Region region = new Region(Integer.parseInt(recordParts[0]),
                        recordParts[1]);
                if (regionDao.find(region.getId()) == null) {
                    regionDao.save(region);
                }


                Distr distr = new Distr(Integer.parseInt(recordParts[4]),
                        recordParts[3],
                        region);
                if (distrDao.find(distr.getId()) == null) {
                    distrDao.save(distr);
                }


                Lka lka = new Lka(Integer.parseInt(recordParts[6]),
                        recordParts[5]);
                if (lkaDao.find(lka.getId()) == null) {
                    lkaDao.save(lka);
                }


                //todo: add report type
                ReportType reportType = reportTypeDao.find(5);

                Responsibility responsibility = new Responsibility(reportType, distr, null);
                List<Responsibility> allResps = responsibilityDao.findAll();

                if (!allResps.contains(responsibility)) {
                    responsibilityDao.save(responsibility);
                }


                ClientCard clientCard = new ClientCard(Integer.parseInt(recordParts[7]),
                        recordParts[8],
                        recordParts[9],
                        recordParts[14],
                        false,
                        distr,
                        recordParts[2],
                        Integer.parseInt(recordParts[10]),
                        lka);

                if (clientCardDao.find(clientCard.getClientId()) == null) {
                    clientCardDao.save(clientCard);
                } else {
                    clientCardDao.update(clientCard);
                }


                Timestamp photoDate = Timestamp.valueOf(recordParts[11]);
                LocalDateTime photoDateLocal = photoDate.toLocalDateTime();
                Timestamp addDate = Timestamp.valueOf(recordParts[12]);
                LocalDateTime addDateLocal = addDate.toLocalDateTime();
                String shortUrl = recordParts[13];
                String day = Integer.toString(addDateLocal.getDayOfMonth()).length() == 1 ? "0" + addDateLocal.getDayOfMonth() : Integer.toString(addDateLocal.getDayOfMonth());
                String month = Integer.toString(addDateLocal.getMonthValue()).length() == 1 ? "0" + addDateLocal.getMonthValue() : Integer.toString(addDateLocal.getMonthValue());
                String fullUrl = "https://report.ncsd.ru/upload/foto100g3/" + addDateLocal.getYear() + "_" + month +
                        "/" + day + "/" + shortUrl;

                PhotoCard photoCard = new PhotoCard(clientCard.getClientId(),
                        fullUrl,
                        photoDateLocal,
                        addDateLocal,
                        recordParts[15],
                        false,
                        reportType);
                if (photoCardDao.findByUrl(photoCard.getUrl()) == null) {
                    photoCardDao.save(photoCard);
                }

                recordCounter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordCounter;
    }
}
