package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.*;
import com.photochecker.dao.common.EmployeeDao;
import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.dao.mlka.NkaTypeDao;
import com.photochecker.model.common.*;
import com.photochecker.model.mlka.Employee;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.model.mlka.NkaType;
import com.photochecker.service.common.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
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
    @Autowired
    private NkaTypeDao nkaTypeDao;
    @Autowired
    private NkaRespDao nkaRespDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private FormatTypeDao formatTypeDao;

    private List<Region> regionList;
    private List<Distr> distrList;
    private List<Channel> channelList;
    private List<Lka> lkaList;
    private List<Responsibility> responsibilityList;
    private List<NkaType> nkaTypeList;
    private List<NkaResp> nkaRespList;
    private List<Employee> employeeList;
    private List<PhotoCard> photoCardList;
    private List<ReportType> reportTypeList;
    private List<FormatType> formatTypeList;

    @Override
    public String uploadDatas(BufferedReader reader, String date) {

        regionList = regionDao.findAll();
        distrList = distrDao.findAll();
        channelList = channelDao.findAll();
        lkaList = lkaDao.findAll();
        responsibilityList = responsibilityDao.findAll();
        nkaTypeList = nkaTypeDao.findAll();
        nkaRespList = nkaRespDao.findAll();
        employeeList = employeeDao.findAll();
        reportTypeList = reportTypeDao.findAll();
        formatTypeList = formatTypeDao.findAll();

        LocalDate dateAdd = LocalDate.parse(date);
        photoCardList = photoCardDao.findAllByDates(dateAdd, dateAdd);

        int lkaCounter = 0;
        int lkaDmpCounter = 0;
        int mlkaCounter = 0;
        int nkaCounter = 0;

        try {
            String record = reader.readLine();

            if (record.equals("--lka begin--"))
                lkaCounter = readLkaDatas(reader);

            record = reader.readLine();

            if (record.equals("--lkaDmp begin--"))
                lkaDmpCounter = readLkaDmpDatas(reader);

            record = reader.readLine();

            if (record.equals("--mlka begin--"))
                mlkaCounter = readMlkaDatas(reader);

            record = reader.readLine();

            if (record.equals("--nka begin--"))
                nkaCounter = readNkaDatas(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Локальные сети: " + lkaCounter + " строк. <br>" +
                "Дистрибьюторы, ДМП: " + lkaDmpCounter + " строк. <br>" +
                "MLKA в федеральных сетях: " + mlkaCounter + " строк. <br>" +
                "Федеральные сети (RJKAM): " + nkaCounter + " строк.";
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


                ReportType reportType = reportTypeList.stream()
                        .filter(reportType1 -> reportType1.getId() == 5)
                        .findFirst()
                        .get();

                Responsibility responsibility = new Responsibility(reportType, distr, null);

                if (!responsibilityList.contains(responsibility)) {
                    responsibilityDao.save(responsibility);
                    responsibilityList.add(responsibility);
                }


                List<FormatType> result = formatTypeList.stream()
                        .filter(formatType1 -> formatType1.getName().equals(recordParts[14]))
                        .collect(Collectors.toList());

                FormatType formatType = null;
                if (result.size() > 0) {
                    formatType = result.get(0);
                } else {
                    formatType = new FormatType(0, recordParts[14]);
                    int id = formatTypeDao.save(formatType);
                    formatType.setId(id);
                    formatTypeList.add(formatType);
                }

                ClientCard clientCard = new ClientCard(Integer.parseInt(recordParts[7]),
                        recordParts[8],
                        recordParts[9],
                        formatType,
                        0,
                        distr,
                        recordParts[2],
                        Integer.parseInt(recordParts[10]),
                        lka,
                        0);

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


                ReportType reportType = reportTypeList.stream()
                        .filter(reportType1 -> reportType1.getId() == 1)
                        .findFirst()
                        .get();

                Responsibility responsibility = new Responsibility(reportType, distr, null);

                if (!responsibilityList.contains(responsibility)) {
                    responsibilityDao.save(responsibility);
                    responsibilityList.add(responsibility);
                }


                List<FormatType> result = formatTypeList.stream()
                        .filter(formatType1 -> formatType1.getName().equals(recordParts[15]))
                        .collect(Collectors.toList());

                FormatType formatType = null;
                if (result.size() > 0) {
                    formatType = result.get(0);
                } else {
                    formatType = new FormatType(0, recordParts[15]);
                    int id = formatTypeDao.save(formatType);
                    formatType.setId(id);
                    formatTypeList.add(formatType);
                }


                ClientCard clientCard = new ClientCard(Integer.parseInt(recordParts[7]),
                        recordParts[8],
                        recordParts[9],
                        formatType,
                        0,
                        distr,
                        recordParts[2],
                        Integer.parseInt(recordParts[10]),
                        lka,
                        0);

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
                        reportType,
                        0,
                        22,
                        "ДМП");
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

    private int readMlkaDatas(BufferedReader reader) {
        int recordCounter = 0;

        String record;

        try {
            while (!(record = reader.readLine()).equals("--mlka end--")) {

                String[] recordParts = record.split("; ");

                if (recordParts.length < 20) continue;


                Region region = new Region(Integer.parseInt(recordParts[1]),
                        recordParts[2]);
                if (!regionList.contains(region)) {
                    regionDao.save(region);
                    regionList.add(region);
                }


                Distr distr = new Distr(Integer.parseInt(recordParts[5]),
                        recordParts[4],
                        region);
                if (!distrList.contains(distr)) {
                    distrDao.save(distr);
                    distrList.add(distr);
                }

                Channel channel = new Channel(Integer.parseInt(recordParts[13]),
                        recordParts[14]);
                if (!channelList.contains(channel)) {
                    channelDao.save(channel);
                    channelList.add(channel);
                }


                Lka lka = null;
                if (channel.getId() == 1) {
                    lka = new Lka(Integer.parseInt(recordParts[7]),
                            recordParts[6]);
                    if (!lkaList.contains(lka)) {
                        lkaDao.save(lka);
                        lkaList.add(lka);
                    }
                }


                ReportType reportType = reportTypeList.stream()
                        .filter(reportType1 -> reportType1.getId() == 2)
                        .findFirst()
                        .get();

                NkaType nkaType = recordParts[0].equals("X5")
                        ? new NkaType(1, "X5")
                        : new NkaType(2, "Тандер");

                if (!nkaTypeList.contains(nkaType)) {
                    nkaTypeDao.save(nkaType);
                    nkaTypeList.add(nkaType);
                }


                Employee employee = null;
                int employeeId = Integer.parseInt(recordParts[9]);

                if (employeeId != 0) {
                    employee = new Employee(Integer.parseInt(recordParts[9]),
                            recordParts[8]);
                    if (!employeeList.contains(employee)) {
                        employeeDao.save(employee);
                        employeeList.add(employee);
                    }
                }

                NkaResp nkaResp = new NkaResp(nkaType, distr, null);

                if (!nkaRespList.contains(nkaResp)) {
                    nkaRespDao.save(nkaResp);
                    nkaRespList.add(nkaResp);
                }


                List<FormatType> result = formatTypeList.stream()
                        .filter(formatType1 -> formatType1.getName().equals(recordParts[18]))
                        .collect(Collectors.toList());

                FormatType formatType = null;
                if (result.size() > 0) {
                    formatType = result.get(0);
                } else {
                    formatType = new FormatType(0, recordParts[18]);
                    int id = formatTypeDao.save(formatType);
                    formatType.setId(id);
                    formatTypeList.add(formatType);
                }


                ClientCard clientCard = new ClientCard(Integer.parseInt(recordParts[10]),
                        recordParts[11],
                        recordParts[12],
                        formatType,
                        0,
                        distr,
                        recordParts[3],
                        Integer.parseInt(recordParts[13]),
                        lka,
                        nkaType.getId());

                if (clientCardDao.find(clientCard.getClientId()) == null) {
                    clientCardDao.save(clientCard);
                } else {
                    clientCardDao.update(clientCard);
                }


                Timestamp photoDate = Timestamp.valueOf(recordParts[15]);
                LocalDateTime photoDateLocal = photoDate.toLocalDateTime();
                Timestamp addDate = Timestamp.valueOf(recordParts[16]);
                LocalDateTime addDateLocal = addDate.toLocalDateTime();
                String shortUrl = recordParts[17];
                String day = Integer.toString(addDateLocal.getDayOfMonth()).length() == 1 ? "0" + addDateLocal.getDayOfMonth() : Integer.toString(addDateLocal.getDayOfMonth());
                String month = Integer.toString(addDateLocal.getMonthValue()).length() == 1 ? "0" + addDateLocal.getMonthValue() : Integer.toString(addDateLocal.getMonthValue());
                String fullUrl = "https://report.ncsd.ru/upload/foto100g3/" + addDateLocal.getYear() + "_" + month +
                        "/" + day + "/" + shortUrl;

                PhotoCard photoCard = new PhotoCard(clientCard.getClientId(),
                        fullUrl,
                        photoDateLocal,
                        addDateLocal,
                        recordParts[19],
                        false,
                        reportType,
                        employee.getId(),
                        23,
                        "Фед. сети");

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

    private int readNkaDatas(BufferedReader reader) {

        int recordCounter = 0;

        String record;

        try {
            while (!(record = reader.readLine()).equals("--nka end--")) {

                String[] recordParts = record.split("; ");

                if (recordParts.length < 14) continue;


                Employee employee = new Employee(Integer.parseInt(recordParts[1]), recordParts[0]);
                if (!employeeList.contains(employee)) {
                    employeeDao.save(employee);
                    employeeList.add(employee);
                }


                Lka lka = new Lka(Integer.parseInt(recordParts[2]),
                        recordParts[3]);
                if (!lkaList.contains(lka)) {
                    lkaDao.save(lka);
                    lkaList.add(lka);
                }


                ReportType reportType = reportTypeList.stream()
                        .filter(reportType1 -> reportType1.getId() == 3)
                        .findFirst()
                        .get();


                List<FormatType> result = formatTypeList.stream()
                        .filter(formatType1 -> formatType1.getName().equals(recordParts[10]))
                        .collect(Collectors.toList());

                FormatType formatType = null;
                if (result.size() > 0) {
                    formatType = result.get(0);
                } else {
                    formatType = new FormatType(0, recordParts[10]);
                    int id = formatTypeDao.save(formatType);
                    formatType.setId(id);
                    formatTypeList.add(formatType);
                }


                ClientCard clientCard = new ClientCard(Integer.parseInt(recordParts[4]),
                        recordParts[5],
                        recordParts[6],
                        formatType,
                        0,
                        new Distr(0, "", new Region(12, "Сети")),
                        null,
                        0,
                        lka,
                        0);

                if (clientCardDao.find(clientCard.getClientId()) == null) {
                    clientCardDao.save(clientCard);
                } else {
                    clientCardDao.update(clientCard);
                }


                Timestamp photoDate = Timestamp.valueOf(recordParts[7]);
                LocalDateTime photoDateLocal = photoDate.toLocalDateTime();
                Timestamp addDate = Timestamp.valueOf(recordParts[8]);
                LocalDateTime addDateLocal = addDate.toLocalDateTime();
                String shortUrl = recordParts[9];
                String day = Integer.toString(addDateLocal.getDayOfMonth()).length() == 1 ? "0" + addDateLocal.getDayOfMonth() : Integer.toString(addDateLocal.getDayOfMonth());
                String month = Integer.toString(addDateLocal.getMonthValue()).length() == 1 ? "0" + addDateLocal.getMonthValue() : Integer.toString(addDateLocal.getMonthValue());
                String fullUrl = "https://report.ncsd.ru/upload/foto100g3/" + addDateLocal.getYear() + "_" + month +
                        "/" + day + "/" + shortUrl;

                PhotoCard photoCard = new PhotoCard(clientCard.getClientId(),
                        fullUrl,
                        photoDateLocal,
                        addDateLocal,
                        recordParts[11],
                        false,
                        reportType,
                        employee.getId(),
                        recordParts[12].length() > 0 ? Integer.parseInt(recordParts[12]) : 0,
                        recordParts[13]);
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
