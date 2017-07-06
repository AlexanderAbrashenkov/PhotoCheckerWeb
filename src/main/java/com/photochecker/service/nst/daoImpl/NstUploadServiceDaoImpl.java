package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.nst.*;
import com.photochecker.model.common.PhotoCard;
import com.photochecker.model.common.ReportType;
import com.photochecker.model.nst.*;
import com.photochecker.service.nst.NstUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class NstUploadServiceDaoImpl implements NstUploadService {
    @Autowired
    private NstClientCardDao nstClientCardDao;
    @Autowired
    private NstClientCriteriasDao nstClientCriteriasDao;
    @Autowired
    private NstOblDao nstOblDao;
    @Autowired
    private NstFormatDao nstFormatDao;
    @Autowired
    private NstRespDao nstRespDao;
    @Autowired
    private PhotoCardDao photoCardDao;
    @Autowired
    private ReportTypeDao reportTypeDao;

    private List<NstClientCard> nstClientCardList;
    private List<NstClientCriterias> nstClientCriteriasList;
    private List<NstObl> nstOblList;
    private List<NstFormat> nstFormatList;
    private List<NstResp> nstRespList;
    private List<PhotoCard> photoCardList;
    private List<ReportType> reportTypeList;

    @Override
    public String uploadDatas(BufferedReader reader, String dateFromS, String dateToS) {

        LocalDate dateFrom = LocalDate.parse(dateFromS, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate dateTo = LocalDate.parse(dateToS, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        nstClientCardList = nstClientCardDao.findAll();
        nstClientCriteriasList = nstClientCriteriasDao.findAllByDates(dateFrom, dateTo);
        nstOblList = nstOblDao.findAll();
        nstFormatList = nstFormatDao.findAll();
        nstRespList = nstRespDao.findAll();
        photoCardList = photoCardDao.findAllByDatesAndReport(dateFrom, dateTo, 4);
        reportTypeList = reportTypeDao.findAll();


        int nstCounter = 0;

        try {
            String record = reader.readLine();

            if (record.equals("--nst begin--"))
                nstCounter = readNstDatas(reader, dateFrom, dateTo);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Фото НСТ: " + nstCounter + " строк. <br>";
    }


    private int readNstDatas(BufferedReader reader, LocalDate dateFrom, LocalDate dateTo) {

        int recordCounter = 0;

        String record;

        try {
            while (!(record = reader.readLine()).equals("--nst end--")) {

                String[] recordParts = record.split(";");

                if (recordParts.length < 5) continue;

                NstFormat nstFormat = new NstFormat(Integer.parseInt(recordParts[0]), null);
                nstFormat = nstFormatList.get(nstFormatList.indexOf(nstFormat));

                NstObl nstObl = new NstObl(0, recordParts[1]);
                if (!nstOblList.contains(nstObl)) {
                    int id = nstOblDao.save(nstObl);
                    nstObl.setId(id);
                    nstOblList.add(nstObl);
                } else {
                    nstObl = nstOblList.get(nstOblList.indexOf(nstObl));
                }


                NstClientCard nstClientCard = new NstClientCard(0, recordParts[2], nstObl, nstFormat, 0);
                if (!nstClientCardList.contains(nstClientCard)) {
                    int id = nstClientCardDao.save(nstClientCard);
                    nstClientCard.setId(id);
                    nstClientCardList.add(nstClientCard);
                } else {
                    nstClientCard = nstClientCardList.get(nstClientCardList.indexOf(nstClientCard));
                }

                NstResp nstResp = new NstResp(nstFormat, nstObl, null);
                if (!nstRespList.contains(nstResp)) {
                    int id = nstRespDao.save(nstResp);
                    nstRespList.add(nstResp);
                }

                if (recordParts[3].equals("-")) {
                    NstClientCriterias nstClientCriterias = new NstClientCriterias(nstClientCard.getId(), dateFrom, dateTo,
                            LocalDateTime.now(), -1, false, false, false, false, false, false, "",
                            false, false, false, false, false, false, "",
                            false, false, false, false, false, "");
                    if (!nstClientCriteriasList.contains(nstClientCriterias)) {
                        nstClientCriteriasDao.save(nstClientCriterias);
                        nstClientCriteriasList.add(nstClientCriterias);
                    }
                    recordCounter++;
                    continue;
                }

                ReportType reportType = reportTypeList.stream()
                        .filter(reportType1 -> reportType1.getId() == 4)
                        .findFirst()
                        .get();

                LocalDate photoDateLocal = LocalDate.parse(recordParts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDateTime photoTime = photoDateLocal.atStartOfDay();
                String fullUrl = recordParts[4];

                PhotoCard photoCard = new PhotoCard(nstClientCard.getId(),
                        fullUrl,
                        photoTime,
                        photoTime,
                        "",
                        false,
                        reportType,
                        0,
                        0,
                        null);
                if (!photoCardList.contains(photoCard)) {
                    photoCardDao.save(photoCard);
                    photoCardList.add(photoCard);
                }

                recordCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordCounter;
    }
}