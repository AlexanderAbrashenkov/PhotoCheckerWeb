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
    private ChannelDao channelDao;
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

    private List<Region> regionList;
    private List<Distr> distrList;
    private List<Channel> channelList;
    private List<Lka> lkaList;
    private List<Responsibility> responsibilityList;

    @Override
    public String uploadDatas(BufferedReader reader, String date) {

        regionList = regionDao.findAll();
        distrList = distrDao.findAll();
        channelList = channelDao.findAll();
        lkaList = lkaDao.findAll();
        responsibilityList = responsibilityDao.findAll();

        LocalDate dateAdd = LocalDate.parse(date);
        int lkaCounter = 0;
        int lkaDmpCounter = 0;

        try {
            String record = reader.readLine();

            if (record.equals("--lka begin--"))
                lkaCounter = readLkaDatas(reader);

            record = reader.readLine();

            if (record.equals("--lkaDmp begin--")) {
                lkaDmpCounter = readLkaDmpDatas(reader);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Локальные сети: " + lkaCounter + " строк. <br>" +
                "Дистрибьюторы, ДМП: " + lkaDmpCounter + " строк.";
    }

    private int readLkaDatas(BufferedReader reader) {

        int recordCounter = 0;

        String record;

        try {
            while (!(record = reader.readLine()).equals("--lka end--")) {

                String[] recordParts = record.split("; ");

                if (recordParts.length < 16) continue;


                Region region = new Region(Integer.parseInt(recordParts[0]),
                        recordParts[1]);
                if (!regionList.contains(region)) {
                    regionDao.save(region);
                    regionList.add(region);
                }


                Distr distr = new Distr(Integer.parseInt(recordParts[4]),
                        recordParts[3],
                        region);
                if (!distrList.contains(distr)) {
                    distrDao.save(distr);
                    distrList.add(distr);
                }


                Lka lka = new Lka(Integer.parseInt(recordParts[6]),
                        recordParts[5]);
                if (!lkaList.contains(lka)) {
                    lkaDao.save(lka);
                    lkaList.add(lka);
                }


                ReportType reportType = reportTypeDao.find(5);

                Responsibility responsibility = new Responsibility(reportType, distr, null);

                if (!responsibilityList.contains(responsibility)) {
                    responsibilityDao.save(responsibility);
                    responsibilityList.add(responsibility);
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

    private int readLkaDmpDatas(BufferedReader reader) {
        int recordCounter = 0;

        String record;

        try {
            while (!(record = reader.readLine()).equals("--lkaDmp end--")) {

                String[] recordParts = record.split("; ");

                if (recordParts.length < 17) continue;


                Region region = new Region(Integer.parseInt(recordParts[0]),
                        recordParts[1]);
                if (!regionList.contains(region)) {
                    regionDao.save(region);
                    regionList.add(region);
                }


                Distr distr = new Distr(Integer.parseInt(recordParts[4]),
                        recordParts[3],
                        region);
                if (!distrList.contains(distr)) {
                    distrDao.save(distr);
                    distrList.add(distr);
                }

                Channel channel = new Channel(Integer.parseInt(recordParts[10]),
                        recordParts[11]);
                if (!channelList.contains(channel)) {
                    channelDao.save(channel);
                    channelList.add(channel);
                }


                Lka lka = null;
                if (channel.getId() == 1) {
                    lka = new Lka(Integer.parseInt(recordParts[6]),
                            recordParts[5]);
                    if (!lkaList.contains(lka)) {
                        lkaDao.save(lka);
                        lkaList.add(lka);
                    }
                }


                ReportType reportType = reportTypeDao.find(1);

                Responsibility responsibility = new Responsibility(reportType, distr, null);

                if (!responsibilityList.contains(responsibility)) {
                    responsibilityDao.save(responsibility);
                    responsibilityList.add(responsibility);
                }


                ClientCard clientCard = new ClientCard(Integer.parseInt(recordParts[7]),
                        recordParts[8],
                        recordParts[9],
                        recordParts[15],
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


                Timestamp photoDate = Timestamp.valueOf(recordParts[12]);
                LocalDateTime photoDateLocal = photoDate.toLocalDateTime();
                Timestamp addDate = Timestamp.valueOf(recordParts[13]);
                LocalDateTime addDateLocal = addDate.toLocalDateTime();
                String shortUrl = recordParts[14];
                String day = Integer.toString(addDateLocal.getDayOfMonth()).length() == 1 ? "0" + addDateLocal.getDayOfMonth() : Integer.toString(addDateLocal.getDayOfMonth());
                String month = Integer.toString(addDateLocal.getMonthValue()).length() == 1 ? "0" + addDateLocal.getMonthValue() : Integer.toString(addDateLocal.getMonthValue());
                String fullUrl = "https://report.ncsd.ru/upload/foto100g3/" + addDateLocal.getYear() + "_" + month +
                        "/" + day + "/" + shortUrl;

                PhotoCard photoCard = new PhotoCard(clientCard.getClientId(),
                        fullUrl,
                        photoDateLocal,
                        addDateLocal,
                        recordParts[16],
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
