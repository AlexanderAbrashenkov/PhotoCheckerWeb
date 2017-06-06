
package com.photochecker.apache_poi;

import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.model.lka.LkaReportItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ApachePoiLka extends AbstractApachePoi implements ApachePoi {

    @Override
    public void createConcreteSheet(String net, List activities) {
        rowIndex = 4;
        spreadsheet = workbook.createSheet(net);
        XSSFRow row = spreadsheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Отчет по проверке фото в локальных сетях c " + dateFrom + " по " + dateTo);
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        titleStyle = workbook.createCellStyle();
        titleStyle.setFont(boldFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(titleStyle);

        // цвета для заливки
        lightRed = new XSSFColor(new Color(252, 228, 214));
        lightGreen = new XSSFColor(new Color(226, 239, 218));
        lightBlue = new XSSFColor(new Color(221, 235, 247));
        lightViolet = new XSSFColor(new Color(255, 229, 251));
        lightPurple = new XSSFColor(new Color(228, 223, 236));

        // набор стилей для обычных заголовков
        headerStyle = createBorderedStyle();
        headerStyle.setFont(boldFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);

        // набор стилей для ТГ
        headerStyle1 = createBorderedStyle();
        headerStyle1.setFont(boldFont);
        headerStyle1.setAlignment(HorizontalAlignment.CENTER);
        headerStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle1.setWrapText(true);
        headerStyle1.setFillForegroundColor(lightBlue);
        headerStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle2 = (XSSFCellStyle) headerStyle1.clone();
        headerStyle2.setFillForegroundColor(lightRed);
        headerStyle3 = (XSSFCellStyle) headerStyle1.clone();
        headerStyle3.setFillForegroundColor(lightGreen);
        headerStyleM = (XSSFCellStyle) headerStyle1.clone();
        headerStyleM.setFillForegroundColor(lightViolet);
        headerStyle4 = (XSSFCellStyle) headerStyle1.clone();
        headerStyle4.setFillForegroundColor(lightPurple);

        // заполняем список акций
        /*ArrayList<TMAActivity> activitiesList = (ArrayList<TMAActivity>) activities;
        if (activitiesList != null && activitiesList.size() > 0) {
            activityMap = new HashMap<>();
            for (int i = 0; i < activitiesList.size(); i++) {
                activityMap.put(activitiesList.get(i), 25 + i * 2);
            }
        }*/

        row = spreadsheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("№");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(1);
        cell.setCellValue("Регион");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(2);
        cell.setCellValue("Дистрибьютор");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(3);
        cell.setCellValue("ID сети");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(4);
        cell.setCellValue("Наименование сети");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(5);
        cell.setCellValue("Тип точки");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(6);
        cell.setCellValue("Код клиента");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(7);
        cell.setCellValue("Наименование клиента");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(8);
        cell.setCellValue("Адрес клиента");
        cell.setCellStyle(headerStyle);
        cell = row.createCell(9);
        cell.setCellValue("Дата");
        cell.setCellStyle(headerStyle);


        cell = row.createCell(10);
        cell.setCellValue("Майонез");
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(11);
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(12);
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(13);
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(14);
        cell.setCellStyle(headerStyle1);

        cell = row.createCell(15);
        cell.setCellValue("Кетчуп");
        cell.setCellStyle(headerStyle2);
        cell = row.createCell(16);
        cell.setCellStyle(headerStyle2);
        cell = row.createCell(17);
        cell.setCellStyle(headerStyle2);
        cell = row.createCell(18);
        cell.setCellStyle(headerStyle2);

        cell = row.createCell(19);
        cell.setCellValue("Соус");
        cell.setCellStyle(headerStyle3);
        cell = row.createCell(20);
        cell.setCellStyle(headerStyle3);
        cell = row.createCell(21);
        cell.setCellStyle(headerStyle3);
        cell = row.createCell(22);
        cell.setCellStyle(headerStyle3);

        cell = row.createCell(23);
        cell.setCellValue("Масло");
        cell.setCellStyle(headerStyleM);
        cell = row.createCell(24);
        cell.setCellStyle(headerStyleM);
        cell = row.createCell(25);
        cell.setCellStyle(headerStyleM);
        cell = row.createCell(26);
        cell.setCellStyle(headerStyleM);

        cell = row.createCell(27);
        cell.setCellValue("Количество нарушений");
        cell.setCellStyle(headerStyle4);
        cell = row.createCell(28);
        cell.setCellValue("Наличие out of stock");
        cell.setCellStyle(headerStyle4);
        cell = row.createCell(29);
        cell.setCellValue("Комментарии");
        cell.setCellStyle(headerStyle4);
        cell = row.createCell(30);
        cell.setCellValue("Дата проверки");
        cell.setCellStyle(headerStyle4);

       /* if (activityMap != null) {
            for (Map.Entry<TMAActivity, Integer> pair : activityMap.entrySet()) {
                int colNum = pair.getValue();
                cell = row.createCell(colNum);
                cell.setCellValue(pair.getKey().getActivityName());
                cell.setCellStyle(headerStyle);
                cell = row.createCell(colNum + 1);
                cell.setCellStyle(headerStyle);
                spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, colNum, colNum + 1));
            }
        }*/

        row = spreadsheet.createRow(3);
        row.setHeight((short) 1200);
        cell = row.createCell(0);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(1);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(2);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(3);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(4);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(7);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(8);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(9);
        cell.setCellStyle(headerStyle);

        cell = row.createCell(10);
        cell.setCellValue("Наличие фотоотчета");
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(11);
        cell.setCellValue("Наличие МЗ Mr.Ricco Авокадо");
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(12);
        cell.setCellValue("Корректность фото");
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(13);
        cell.setCellValue("Доля полки");
        cell.setCellStyle(headerStyle1);
        cell = row.createCell(14);
        cell.setCellValue("Бренд-блок / Вертикальный блок");
        cell.setCellStyle(headerStyle1);

        cell = row.createCell(15);
        cell.setCellValue("Наличие фотоотчета");
        cell.setCellStyle(headerStyle2);
        cell = row.createCell(16);
        cell.setCellValue("Корректность фото");
        cell.setCellStyle(headerStyle2);
        cell = row.createCell(17);
        cell.setCellValue("Доля полки");
        cell.setCellStyle(headerStyle2);
        cell = row.createCell(18);
        cell.setCellValue("Бренд-блок / Вертикальный блок");
        cell.setCellStyle(headerStyle2);

        cell = row.createCell(19);
        cell.setCellValue("Наличие фотоотчета");
        cell.setCellStyle(headerStyle3);
        cell = row.createCell(20);
        cell.setCellValue("Корректность фото");
        cell.setCellStyle(headerStyle3);
        cell = row.createCell(21);
        cell.setCellValue("Доля полки");
        cell.setCellStyle(headerStyle3);
        cell = row.createCell(22);
        cell.setCellValue("Бренд-блок / Вертикальный блок");
        cell.setCellStyle(headerStyle3);

        cell = row.createCell(23);
        cell.setCellValue("Наличие фотоотчета");
        cell.setCellStyle(headerStyleM);
        cell = row.createCell(24);
        cell.setCellValue("Корректность фото");
        cell.setCellStyle(headerStyleM);
        cell = row.createCell(25);
        cell.setCellValue("Доля полки");
        cell.setCellStyle(headerStyleM);
        cell = row.createCell(26);
        cell.setCellValue("Бренд-блок / Вертикальный блок");
        cell.setCellStyle(headerStyleM);

        cell = row.createCell(27);
        cell.setCellStyle(headerStyle4);
        cell = row.createCell(28);
        cell.setCellStyle(headerStyle4);
        cell = row.createCell(29);
        cell.setCellStyle(headerStyle4);
        cell = row.createCell(30);
        cell.setCellStyle(headerStyle4);

        /*if (activityMap != null) {
            for (Map.Entry<TMAActivity, Integer> pair : activityMap.entrySet()) {
                int colNum = pair.getValue();
                cell = row.createCell(colNum);
                cell.setCellValue("Желтый ценник");
                cell.setCellStyle(headerStyle);
                cell = row.createCell(colNum + 1);
                cell.setCellValue("Наличие OOS");
                cell.setCellStyle(headerStyle);
                spreadsheet.setColumnWidth(colNum, 3000);
                spreadsheet.setColumnWidth(colNum + 1, 3000);
            }
        }*/

        // Объединение ячеек
        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 30));

        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 2, 2));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 3));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 4, 4));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 6, 6));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 7, 7));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 8, 8));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 9, 9));

        spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 14));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, 15, 18));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, 19, 22));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, 23, 26));

        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 27, 27));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 28, 28));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 29, 29));
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 3, 30, 30));

        //ширина столбцов
        spreadsheet.setColumnWidth(2, 7000);
        spreadsheet.setColumnWidth(4, 5000);
        spreadsheet.setColumnWidth(5, 5000);
        spreadsheet.setColumnWidth(6, 4000);
        spreadsheet.setColumnWidth(7, 8000);
        spreadsheet.setColumnWidth(8, 13000);
        spreadsheet.setColumnWidth(9, 4000);

        for (int i = 10; i < 29; i++) {
            spreadsheet.setColumnWidth(i, 3000);
        }
        spreadsheet.setColumnWidth(29,13000);
        spreadsheet.setColumnWidth(30,4000);
    }

    @Override
    public void writeOneTtToConcreteSheet(List parameters) {

        LkaReportItem lkaReportItem = (LkaReportItem) parameters.get(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String region = lkaReportItem.getRegion();
        String distr = lkaReportItem.getDistr();
        int lkaId = lkaReportItem.getLkaId();
        String lkaName = lkaReportItem.getLkaName();
        String type = lkaReportItem.getClientType();
        int clientId = lkaReportItem.getClientId();
        String clientName = lkaReportItem.getClientName();
        String clientAddress = lkaReportItem.getClientAddress();
        ClientCriterias clientCriterias = lkaReportItem.getClientCriterias();
        LocalDate photo_date = lkaReportItem.getPhoto_date();
        //List<TMAActivity> activities = lkaAddress.getTmaActivityList();

        // стили
        XSSFCellStyle leftTextStyle = createBorderedStyle();
        XSSFCellStyle centerTextStyle = createBorderedStyle();
        centerTextStyle.setAlignment(HorizontalAlignment.CENTER);

        if (clientCriterias.getSaveDate() != null) {
            leftTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            centerTextStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            leftTextStyle.setFillForegroundColor(new XSSFColor(new Color(170, 250, 200)));
            centerTextStyle.setFillForegroundColor(new XSSFColor(new Color(170, 250, 200)));
        } else {
            leftTextStyle.setFillPattern(FillPatternType.NO_FILL);
            centerTextStyle.setFillPattern(FillPatternType.NO_FILL);
        }

        XSSFRow row = spreadsheet.createRow(rowIndex++);
        Cell cell = row.createCell(0);
        cell.setCellValue(rowIndex - 4);
        cell.setCellStyle(centerTextStyle);
        cell = row.createCell(1);
        if (region != null)
            cell.setCellValue(region.trim());
        cell.setCellStyle(centerTextStyle);
        cell = row.createCell(2);
        if (distr != null)
            cell.setCellValue(distr);
        cell.setCellStyle(leftTextStyle);
        cell = row.createCell(3);
        if (lkaId != 0)
            cell.setCellValue(lkaId);
        cell.setCellStyle(leftTextStyle);
        cell = row.createCell(4);
        if (lkaName != null)
            cell.setCellValue(lkaName);
        cell.setCellStyle(leftTextStyle);
        cell = row.createCell(5);
        if (type != null)
            cell.setCellValue(type);
        cell.setCellStyle(leftTextStyle);
        cell = row.createCell(6);
        cell.setCellValue(clientId);
        cell.setCellStyle(leftTextStyle);
        cell = row.createCell(7);
        if (clientName != null)
            cell.setCellValue(clientName);
        cell.setCellStyle(leftTextStyle);
        cell = row.createCell(8);
        if (clientAddress != null)
            cell.setCellValue(clientAddress);
        cell.setCellStyle(leftTextStyle);
        cell = row.createCell(9);
        cell.setCellValue(photo_date.format(formatter));
        CreationHelper creationHelper = workbook.getCreationHelper();
        XSSFCellStyle dateStyle = (XSSFCellStyle) leftTextStyle.clone();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));
        dateStyle.setAlignment(HorizontalAlignment.GENERAL);
        cell.setCellStyle(dateStyle);

        XSSFCellStyle styleMz = (XSSFCellStyle) centerTextStyle.clone();
        styleMz.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleMz.setFillForegroundColor(lightBlue);
        XSSFCellStyle styleK = (XSSFCellStyle) styleMz.clone();
        styleK.setFillForegroundColor(lightRed);
        XSSFCellStyle styleS = (XSSFCellStyle) styleMz.clone();
        styleS.setFillForegroundColor(lightGreen);
        XSSFCellStyle styleM = (XSSFCellStyle) styleMz.clone();
        styleM.setFillForegroundColor(lightViolet);

        cell = row.createCell(10, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasMz())
            cell.setCellValue(clientCriterias.isHasPhotoMz() ? 1 : 0);
        cell.setCellStyle(styleMz);
        cell = row.createCell(11, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasMz())
            cell.setCellValue(clientCriterias.isHasAddProdMz() ? 1 : 0);
        cell.setCellStyle(styleMz);
        cell = row.createCell(12, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasMz())
            cell.setCellValue(clientCriterias.isCorrectMz() ? 1 : 0);
        cell.setCellStyle(styleMz);
        cell = row.createCell(13, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasMz())
            cell.setCellValue(clientCriterias.isCrit1Mz() ? 1 : 0);
        cell.setCellStyle(styleMz);
        cell = row.createCell(14, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasMz())
            cell.setCellValue(clientCriterias.isCrit2Mz() ? 1 : 0);
        cell.setCellStyle(styleMz);

        cell = row.createCell(15, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasK())
            cell.setCellValue(clientCriterias.isHasPhotoK() ? 1 : 0);
        cell.setCellStyle(styleK);
        cell = row.createCell(16, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasK())
            cell.setCellValue(clientCriterias.isCorrectK() ? 1 : 0);
        cell.setCellStyle(styleK);
        cell = row.createCell(17, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasK())
            cell.setCellValue(clientCriterias.isCrit1K() ? 1 : 0);
        cell.setCellStyle(styleK);
        cell = row.createCell(18, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasK())
            cell.setCellValue(clientCriterias.isCrit2K() ? 1 : 0);
        cell.setCellStyle(styleK);

        cell = row.createCell(19, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasS())
            cell.setCellValue(clientCriterias.isHasPhotoS() ? 1 : 0);
        cell.setCellStyle(styleS);
        cell = row.createCell(20, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasS())
            cell.setCellValue(clientCriterias.isCorrectS() ? 1 : 0);
        cell.setCellStyle(styleS);
        cell = row.createCell(21, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasS())
            cell.setCellValue(clientCriterias.isCrit1S() ? 1 : 0);
        cell.setCellStyle(styleS);
        cell = row.createCell(22, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasS())
            cell.setCellValue(clientCriterias.isCrit2S() ? 1 : 0);
        cell.setCellStyle(styleS);

        cell = row.createCell(23, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasM())
            cell.setCellValue(clientCriterias.isHasPhotoM() ? 1 : 0);
        cell.setCellStyle(styleM);
        cell = row.createCell(24, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasM())
            cell.setCellValue(clientCriterias.isCorrectM() ? 1 : 0);
        cell.setCellStyle(styleM);
        cell = row.createCell(25, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasM())
            cell.setCellValue(clientCriterias.isCrit1M() ? 1 : 0);
        cell.setCellStyle(styleM);
        cell = row.createCell(26, CellType.NUMERIC);
        if (clientCriterias.getSaveDate() != null && clientCriterias.isHasM())
            cell.setCellValue(clientCriterias.isCrit2M() ? 1 : 0);
        cell.setCellStyle(styleM);

        cell = row.createCell(27);
        XSSFCellStyle lastColumnsStyle = (XSSFCellStyle) centerTextStyle.clone();
        lastColumnsStyle.setFillForegroundColor(lightPurple);
        lastColumnsStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(lastColumnsStyle);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula("COUNTIF(N" + rowIndex + ":O" + rowIndex + ",\"=0\")+" +
                "COUNTIF(R" + rowIndex + ":S" + rowIndex + ",\"=0\")+" +
                "COUNTIF(V" + rowIndex + ":W" + rowIndex + ",\"=0\")+" +
                "COUNTIF(Z" + rowIndex + ":AA" + rowIndex + ",\"=0\")");
        cell = row.createCell(28);
        if (clientCriterias.getSaveDate() != null) {
            cell.setCellValue(clientCriterias.isOos() ? 1 : 0);
        }
        cell.setCellStyle(lastColumnsStyle);
        cell = row.createCell(29);
        XSSFCellStyle commentStyle = (XSSFCellStyle) lastColumnsStyle.clone();
        commentStyle.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellValue(clientCriterias.getComment());
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(commentStyle);
        cell = row.createCell(30);
        if (clientCriterias.getSaveDate() != null) {
            cell.setCellValue(clientCriterias.getSaveDate().format(formatter));
        }
        XSSFCellStyle dateStyle1 = (XSSFCellStyle) commentStyle.clone();
        dateStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("dd.MM.yyyy"));
        dateStyle1.setAlignment(HorizontalAlignment.GENERAL);
        cell.setCellStyle(dateStyle1);

        XSSFCellStyle centerErrorStyle = (XSSFCellStyle) centerTextStyle.clone();
        centerErrorStyle.setFillForegroundColor(new XSSFColor(new Color(255, 242, 204)));
        centerErrorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        /*if (activityMap != null && activityMap.size() > 0) {
            for (Map.Entry<TMAActivity, Integer> pair : activityMap.entrySet()) {
                if (activities != null && activities.contains(pair.getKey())) {
                    TMAActivity tmaActivity = activities.get(activities.indexOf(pair.getKey()));
                    int colNum = pair.getValue();
                    cell = row.createCell(colNum);
                    cell.setCellValue(tmaActivity.isSalePrice() ? 1 : 0);
                    cell.setCellStyle(centerTextStyle);
                    cell = row.createCell(colNum + 1);
                    cell.setCellValue(tmaActivity.isHasOos() ? 1 : 0);
                    cell.setCellStyle(centerTextStyle);
                } else {
                    int colNum = pair.getValue();
                    cell = row.createCell(colNum);
                    cell.setCellValue(0);
                    cell.setCellStyle(centerErrorStyle);
                    cell = row.createCell(colNum + 1);
                    cell.setCellValue(0);
                    cell.setCellStyle(centerErrorStyle);
                }
            }
        }*/
    }

    @Override
    public void calcSumRowConcreteSheet(String net) {

        XSSFCellStyle cellStyle = createBorderedStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);
        cellStyle.setFillForegroundColor(new XSSFColor(new Color(253, 255, 23)));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFRow row = spreadsheet.createRow(rowIndex);
        Cell cell = row.createCell(0);
        cell.setCellValue("ИТОГО");
        cell.setCellStyle(cellStyle);
        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(8);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(9);
        cell.setCellStyle(cellStyle);

        for (int i = 10; i < 29; i++) {
            String col;
            if (i < 26) {
                col = "" + (char) ('A' + i);
            } else {
                col = "" + (char) ('A' + i / 26 - 1) + (char) ('A' + i % 26);
            }
            cell = row.createCell(i);
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("SUM(" + col + 5 + ":" + col + rowIndex + ")");
            cell.setCellStyle(cellStyle);
        }
        cell = row.createCell(29);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(30);
        cell.setCellStyle(cellStyle);
        spreadsheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 9));

        /*if (activityMap != null) {
            for (int i = 25; i < 25 + activityMap.size() * 2; i++) {
                String col;
                if (i < 26) {
                    col = "" + (char) ('A' + i);
                } else {
                    col = "" + (char) ('A' + i / 26 - 1) + (char) ('A' + i % 26);
                }
                cell = row.createCell(i);
                cell.setCellType(CellType.FORMULA);
                cell.setCellFormula("SUM(" + col + 5 + ":" + col + rowIndex + ")");
                cell.setCellStyle(cellStyle);
            }
        }*/

        // условное форматирование
        /*SheetConditionalFormatting sheetCF = spreadsheet.getSheetConditionalFormatting();
        ConditionalFormattingRule rule = sheetCF.createConditionalFormattingRule(ComparisonOperator.EQUAL, "0");
        PatternFormatting fill = rule.createPatternFormatting();
        fill.setFillBackgroundColor(new XSSFColor(new Color(255, 204, 204)));
        fill.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
        FontFormatting fontF = rule.createFontFormatting();
        fontF.setFontColorIndex(HSSFColor.RED.index);
        CellRangeAddress[] regions = {
                CellRangeAddress.valueOf("E5:S" + rowIndex)
        };
        sheetCF.addConditionalFormatting(regions, rule);*/

    }

    @Override
    public void createTotalSheet(String net) {

    }

    @Override
    public void createTotalSheetHeader(String net) {

    }

    @Override
    public void writeOneTtToTotalSheet(List parameters) {

    }

    @Override
    public void calcSumRowTotalSheet() {

    }
}
