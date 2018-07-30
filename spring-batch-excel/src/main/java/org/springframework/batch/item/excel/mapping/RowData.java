package org.springframework.batch.item.excel.mapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RowData  {
    List<ColumnData> column = new LinkedList<>();

    public void addColumn(ColumnData columnData) {
        column.add(columnData);
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

    public void addColumn(String columnName, String value) {
        this.addColumn(new ColumnData(columnName,value));
    }
}
