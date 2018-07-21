package org.springframework.batch.item.excel.streamer;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.batch.item.excel.poi.AbstractPoiSheet;

import java.util.Iterator;

public class StreamerSheet extends AbstractPoiSheet {
    Row header;
    Iterator<Row> rows;
    public StreamerSheet(org.apache.poi.ss.usermodel.Sheet sheetAt,int rowNumberOfHeader) {
        this.delegate = sheetAt;
        rows = delegate.iterator();
        int i = 0;
        while (rows.hasNext()) {
            if (i++ == rowNumberOfHeader) {
                header = rows.next();
                break;
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
        if (rows.hasNext()) {
            return poiRowConvert2Array(rows.next());
        }
        return null;
    }

    @Override
    public int getNumberOfColumns() {
        return header.getLastCellNum() + 1;
    }
}
