package org.springframework.batch.item.excel.mapping;

public class ColumnData {
    private int columnNumber;

    private String columnName;
    private Object data;

    public ColumnData(int columnNumber, String columnName, String value) {
        this.columnNumber = columnNumber;
        this.columnName = columnName;
        this.data = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getData() {
        return data.toString();
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
