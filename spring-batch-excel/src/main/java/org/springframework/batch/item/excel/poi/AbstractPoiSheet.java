package org.springframework.batch.item.excel.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.batch.item.excel.Sheet;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPoiSheet implements Sheet {
    protected  org.apache.poi.ss.usermodel.Sheet delegate;
    private FormulaEvaluator evaluator;

    protected String[] poiRowConvert2Array(Row row) {
        final List<String> cells = new LinkedList<String>();

        for (int i = 0; i < getNumberOfColumns(); i++) {
            Cell cell = row.getCell(i);
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        cells.add(String.valueOf(date.getTime()));
                    } else {
                        cells.add(String.valueOf(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cells.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case Cell.CELL_TYPE_STRING:
                case Cell.CELL_TYPE_BLANK:
                    cells.add(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cells.add(getFormulaEvaluator().evaluate(cell).formatAsString());
                    break;
                default:
                    throw new IllegalArgumentException("Cannot handle cells of type " + cell.getCellType());
            }
        }
        return cells.toArray(new String[cells.size()]);
    }

    private FormulaEvaluator getFormulaEvaluator() {
        if (this.evaluator == null) {
            this.evaluator = delegate.getWorkbook().getCreationHelper().createFormulaEvaluator();
        }
        return this.evaluator;
    }
}
