package org.springframework.batch.item.excel.streamer;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.batch.item.excel.AbstractExcelItemReader;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.batch.item.excel.support.rowset.RowSetFactory;
import org.springframework.core.io.Resource;

public class StreamerItemReader<T> extends AbstractExcelItemReader<T> {
    private Workbook workbook;

    private int rowNumberOfColumnNames;

    private int rowCacheSize= 200;
    private int bufferSize  = 1024 * 4;

    private int maxNumberOfSheets = -1;

    public StreamerItemReader() {
    }
    public int getMaxNumberOfSheets() {
        return maxNumberOfSheets;
    }

    public void setMaxNumberOfSheets(int maxNumberOfSheets) {
        this.maxNumberOfSheets = maxNumberOfSheets;
    }

    @Override
    protected Sheet getSheet(int sheet) {
        return new StreamerSheet(workbook.getSheetAt(sheet), rowNumberOfColumnNames);
    }

    @Override
    protected int getNumberOfSheets() {
        if (maxNumberOfSheets > -1 && maxNumberOfSheets <= workbook.getNumberOfSheets()) {
            return maxNumberOfSheets;
        }
        return workbook.getNumberOfSheets();
    }

    @Override
    protected void openExcelFile(Resource resource) throws Exception {
        workbook = StreamingReader.builder().rowCacheSize(rowCacheSize)
        .bufferSize(bufferSize)
        .open(resource.getInputStream())
        ;


    }

    @Override
    public void setRowSetFactory(RowSetFactory rowSetFactory) {
        super.setRowSetFactory(rowSetFactory);
        this.rowNumberOfColumnNames = rowSetFactory.getColumnNameExtractor().getRowNumberOfColumnNames();
    }

    @Override
    protected void doClose() throws Exception {
        if (workbook != null) {
            workbook.close();
        }

    }


    public int getRowCacheSize() {
        return rowCacheSize;
    }

    public void setRowCacheSize(int rowCacheSize) {
        this.rowCacheSize = rowCacheSize;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
