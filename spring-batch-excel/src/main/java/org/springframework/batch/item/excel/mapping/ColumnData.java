package org.springframework.batch.item.excel.mapping;

public class ColumnData {
    private String columnName;
    private Object data;

    public ColumnData(String columnName, Object data) {
        this.columnName = columnName;
        this.data = data;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
