package com.photochecker.model.nst;

import org.apache.poi.ss.usermodel.*;
import org.jxls.area.XlsArea;
import org.jxls.common.AreaListener;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;

/**
 * Created by market6 on 18.07.2017.
 */
public class SimpleAreaListener implements AreaListener {

    XlsArea area;
    PoiTransformer transformer;
    private final CellRef visitCell1 = new CellRef("Template!H5");
    private final CellRef visitCell2 =new CellRef("Template!H11");

    public SimpleAreaListener(XlsArea area) {
        this.area = area;
        transformer = (PoiTransformer) area.getTransformer();
    }

    public void beforeApplyAtCell(CellRef cellRef, Context context) {

    }

    public void afterApplyAtCell(CellRef cellRef, Context context) {

    }

    public void beforeTransformCell(CellRef srcCell, CellRef targetCell, Context context) {

    }

    public void afterTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
        if(visitCell1.equals(srcCell) || visitCell2.equals(srcCell)){ // we are at employee bonus cell
            NstReportItem nstReportItem = (NstReportItem) context.getVar("item");
            if( nstReportItem.getNstRepCrit().getVisitCount() == -1 ){ // highlight bonus when >= 20%
                discolorRow(targetCell);
            }
        }
    }

    private void discolorRow(CellRef cellRef) {
        Workbook workbook = transformer.getWorkbook();
        Sheet sheet = workbook.getSheet(cellRef.getSheetName());
        for (int i = 0; i < 8; i++) {
            processCell(sheet, cellRef, workbook, i);
        }
        for (int i = 26; i < 30; i++) {
            processCell(sheet, cellRef, workbook, i);
        }
    }

    private void processCell(Sheet sheet, CellRef cellRef, Workbook workbook, int i) {
        Cell cell = sheet.getRow(cellRef.getRow()).getCell(i);
        CellStyle cellStyle = cell.getCellStyle();
        CellStyle newCellStyle = workbook.createCellStyle();
        newCellStyle.setDataFormat(cellStyle.getDataFormat());
        newCellStyle.setFont(workbook.getFontAt(cellStyle.getFontIndex()));
        newCellStyle.setFillBackgroundColor(cellStyle.getFillBackgroundColor());
        newCellStyle.setFillPattern(FillPatternType.NO_FILL);
        cell.setCellStyle(newCellStyle);
    }
}
