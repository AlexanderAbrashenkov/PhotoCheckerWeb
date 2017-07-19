package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.nst.*;
import com.photochecker.model.common.ReportType;
import com.photochecker.model.nst.*;
import com.photochecker.service.nst.NstUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

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
    private NstPhotoCardDao nstPhotoCardDao;
    @Autowired
    private ReportTypeDao reportTypeDao;
    @Autowired
    private NstStatDao nstStatDao;
    @Autowired
    ServletContext servletContext;

    @Autowired
    private Properties properties;

    private List<NstClientCard> nstClientCardList;
    private List<NstClientCriterias> nstClientCriteriasList;
    private List<NstObl> nstOblList;
    private List<NstFormat> nstFormatList;
    private List<NstResp> nstRespList;
    private List<NstPhotoCard> nstPhotoCardList;
    private List<ReportType> reportTypeList;

    @Override
    public String uploadDatas(BufferedReader reader, String dateFromS, String dateToS) {

        LocalDate dateFrom = LocalDate.parse(dateFromS, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate dateTo = LocalDate.parse(dateToS, DateTimeFormatter.ofPattern("dd.MM.yyyy"));



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = dateFrom.format(formatter) + "_" + dateTo.format(formatter) + "_nst_photo";

        if (!photoTableName.equals(properties.getProperty("nst.current.week.photo"))) {
            //copy photo from prev to common
            String photoPrev = properties.getProperty("nst.prev.week.photo");
            if (photoPrev.length() > 0) {
                nstPhotoCardDao.copyPhotosToCommon(properties.getProperty("nst.prev.week.photo"));
                nstClientCriteriasDao.copyCritsToCommon(properties.getProperty("nst.prev.week.save"));
            }

            //props current to prev
            properties.setProperty("nst.prev.week.photo", properties.getProperty("nst.current.week.photo"));
            properties.setProperty("nst.prev.week.save", properties.getProperty("nst.current.week.save"));

            //props set current
            String saveTableName = dateFrom.format(formatter) + "_" + dateTo.format(formatter) + "_nst_save";

            properties.setProperty("nst.current.week.photo", photoTableName);
            properties.setProperty("nst.current.week.save", saveTableName);

            File file = new File(servletContext.getRealPath("/resources/properties/prop.properties"));
            try (OutputStream outputStream = new FileOutputStream(file)) {
                properties.store(outputStream, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //create current tables
            nstPhotoCardDao.createCurrentTable(photoTableName);
            nstClientCriteriasDao.createCurrentTable(saveTableName);
            //nstStatDao.createCurrentTable(statTableName);
        }

        nstClientCardList = nstClientCardDao.findAll();
        nstClientCriteriasList = nstClientCriteriasDao.findAllByDates(dateFrom, dateTo);
        nstOblList = nstOblDao.findAll();
        nstFormatList = nstFormatDao.findAll();
        nstRespList = nstRespDao.findAll();
        nstPhotoCardList = nstPhotoCardDao.findAllByDates(dateFrom, dateTo);
        reportTypeList = reportTypeDao.findAll();


        int nstCounter = 0;

        try {
            String record = reader.readLine();

            if (record.equals("--nst begin--"))
                nstCounter = readNstDatas(reader, dateFrom, dateTo);

        } catch (IOException e) {
            e.printStackTrace();
        }

        nstStatDao.fillUpWeekStat(dateFrom, dateTo);

        return "Фото НСТ: " + nstCounter + " строк. <br>";
    }


    private int readNstDatas(BufferedReader reader, LocalDate dateFrom, LocalDate dateTo) {

        int recordCounter = 0;

        String record;

        int i = 0;

        try {
            while (!(record = reader.readLine()).equals("--nst end--")) {

                i++;

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

                NstPhotoCard photoCard = new NstPhotoCard(nstClientCard.getId(),
                        fullUrl,
                        photoTime);
                if (!nstPhotoCardList.contains(photoCard)) {
                    nstPhotoCardDao.save(photoCard);
                    nstPhotoCardList.add(photoCard);
                }

                recordCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recordCounter;
    }
}
