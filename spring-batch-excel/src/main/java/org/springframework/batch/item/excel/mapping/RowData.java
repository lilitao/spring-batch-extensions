package org.springframework.batch.item.excel.mapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RowData  {
    int rowNumber;

    List<ColumnData> column = new LinkedList<>();

    public RowData(int currentRowIndex) {
        this.rowNumber = currentRowIndex;
    }

    public RowData() {

    }

    public List<ColumnData> getColumns() {
        return column;
    }

    public Object getValueByColumnName(String columnName) {
        return column.stream()
                .filter(columnData -> columnName.equals(columnData.getColumnName()))
                .findFirst()
                .get()
                .getData();
    }


    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    private void addColumn(ColumnData columnData) {
        column.add(columnData);
    }

    public void addColumn(int columnNumber, String columnName, String value) {
        this.addColumn(new ColumnData(columnNumber, columnName, value));
    }
}
