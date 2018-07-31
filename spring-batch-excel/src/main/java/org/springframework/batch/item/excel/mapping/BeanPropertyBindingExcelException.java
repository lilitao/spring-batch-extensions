package org.springframework.batch.item.excel.mapping;

public class BeanPropertyBindingExcelException extends RuntimeException {
    public BeanPropertyBindingExcelException(String message, ReflectiveOperationException e) {
        super(message, e);
    }
}
