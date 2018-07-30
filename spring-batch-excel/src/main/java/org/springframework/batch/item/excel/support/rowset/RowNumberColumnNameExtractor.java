/*
 * Copyright 2006-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.item.excel.support.rowset;

import org.springframework.batch.item.excel.Sheet;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@code ColumnNameExtractor} which returns the values of a given row (default is 0) as the column
 * names.
 *
 * @author Marten Deinum
 * @since 0.5.0
 */
public class RowNumberColumnNameExtractor implements ColumnNameExtractor {


    private int rowNumberOfColumnNames;
    private Map<String,String> columnNameMap;



    @Override
    public String[] getColumnNames(final Sheet sheet) {
        String[] names = sheet.getRow(rowNumberOfColumnNames);
        if (columnNameMap != null && !columnNameMap.isEmpty()) {
            names =   Arrays.stream(names)
                    .map(name -> columnNameTransform(name))
                    .collect(Collectors.toList())
                    .toArray(new String[names.length]);
        }


        return names;
    }



    @Override
    public int getRowNumberOfColumnNames() {
        return rowNumberOfColumnNames;
    }

    public void setRowNumberOfColumnNames(int rowNumberOfColumnNames) {
        this.rowNumberOfColumnNames = rowNumberOfColumnNames;
    }




    private String columnNameTransform(String name) {
        if (!columnNameMap.containsKey(name)) {
            return name;
        }
        return  columnNameMap.get(name);
    }


    public void setColumnNameMap(Map<String, String> columnNameMap) {
        this.columnNameMap = columnNameMap;
    }




}
