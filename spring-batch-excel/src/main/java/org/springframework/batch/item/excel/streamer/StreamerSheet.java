package org.springframework.batch.item.excel.streamer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.batch.item.excel.poi.AbstractPoiSheet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StreamerSheet extends AbstractPoiSheet {
    List<Row> preHeader = new LinkedList<>();
    Row header;
    Iterator<Row> rows;
    private int rowNumberOfColumnNames;
    public StreamerSheet(org.apache.poi.ss.usermodel.Sheet sheetAt,int rowNumberOfHeader) {
        this.delegate = sheetAt;
        rows = delegate.iterator();
        this.rowNumberOfColumnNames = rowNumberOfHeader;
        int i = 0;
        while (rows.hasNext()) {
            if (i++ == rowNumberOfHeader) {
                header = rows.next();
                break;
            } else {
                preHeader.add(rows.next());
            }
        }
    }

    @Override
    public int getNumberOfRows() {
        return this.delegate.getLastRowNum() + 1;
    }

    @Override
    public String getName() {
        return delegate.getSheetName();
    }

    @Override
    public String[] getRow(int rowNumber) {
        if (rowNumber == this.rowNumberOfColumnNames) {
            return poiRowConvert2Array(header);
        }
        if (rowNumber < this.rowNumberOfColumnNames) {
            return poiRowConvert2Array(preHeader.get(rowNumber));
        }
        if (rows.hasNext()) {
            return poiRowConvert2Array(rows.next());
        }
        return null;
    }

    @Override
    public int getNumberOfColumns() {
        if (header == null) {
            return 0;
        }
        for (int i = header.getLastCellNum(); i >= 0; i--) {
            if (header.getCell(i) != null && !"".equals(header.getCell(i).getStringCellValue())) {
                return i+1;
            }
        }
        return 0;
    }
}
