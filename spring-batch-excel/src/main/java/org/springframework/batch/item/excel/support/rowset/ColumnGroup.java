package org.springframework.batch.item.excel.support.rowset;

import java.util.*;

/**
 * a instance of this class is used to create and manage a group properties in excel mapping to java bean<br>
 * for example:<br>
 * in excel<br>
 * <pre>
 *   <code>
 *id | name | address | phone | address | phone
 *0  | andy | home    | t     | office  | p
 *   </code>
 * </pre>
 * filed in java bean
 * <pre>
 *     <code>
 * public class Person{
 *   private int id;
 *   private String name;
 *   private List&lt;Contact&gt; contact;
 * }
 *      </code>
 *</pre>
 *
 *  throughout the process , you will find that : 2 Contact bean will be created with address and phone filed from excel and inject into a instance of Person
 */
public class ColumnGroup {


    private String groupName;


    private Map<String, Integer> currentIndex = new HashMap<>();

    public ColumnGroup(String groupName) {
        this.groupName = groupName;
    }

    public synchronized String convert2FullColumnName(String columnName) {
        if (containColumnName(columnName)) {
            Integer index = currentIndex.get(columnName);
            currentIndex.put(columnName, index+1);
            return this.groupName + "[" + index + "]." + columnName;
        }
        return columnName;
    }

    public boolean containColumnName(String columnName) {
        if (!currentIndex.isEmpty()) {
            ArrayList<String> keys = new ArrayList();
            keys.addAll(currentIndex.keySet());
            return keys.contains(columnName);
        }
        return false;
    }

    public void addColumnName(String... columnNames) {
        if (columnNames != null && columnNames.length > 0) {
            Arrays.stream(columnNames)
                    .forEach(name->this.currentIndex.put(name, 0));
        }

    }

    public String getGroupName() {
        return groupName;
    }
}
