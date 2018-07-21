package org.springframework.batch.item.excel.streamer;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.batch.item.excel.AbstractExcelItemReader;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.core.io.Resource;

public class StreamerItemReader<T> extends AbstractExcelItemReader<T> {
    private Workbook workbook;
    private  int rowNumberOfHeader ;

    public StreamerItemReader(int rowNumberOfHeader) {
        this.rowNumberOfHeader = rowNumberOfHeader;
    }

    @Override
    protected Sheet getSheet(int sheet) {
        return new StreamerSheet(workbook.getSheetAt(sheet),rowNumberOfHeader);
    }

    @Override
    protected int getNumberOfSheets() {
        return workbook.getNumberOfSheets();
    }

    @Override
    protected void openExcelFile(Resource resource) throws Exception {
        workbook = StreamingReader.builder().rowCacheSize(200)
        .bufferSize(1024*4)
        .open(resource.getInputStream())
        ;


    }

    @Override
    protected void doClose() throws Exception {
        if (workbook != null) {
            workbook.close();
        }

    }
}
