package org.springframework.batch.item.excel.support.rowset;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnGroupTest {

    @Test
    public void should_get_full_column_name() {
        //give
        ColumnGroup group = new ColumnGroup("subBean");
        group.addColumnName("sid");
        group.addColumnName("name");
        //when
        String fullName = group.convert2FullColumnName("sid");
        //then
        Assertions.assertThat(fullName).isEqualTo("subBean[0].sid");
    }

    @Test
    public void should_get_full_column_name_by_index_2() {
        ColumnGroup group = new ColumnGroup("subBean");
        group.addColumnName("sid");
        group.addColumnName("name");
        Assertions.assertThat(group.convert2FullColumnName("sid")).isEqualTo("subBean[0].sid");
        Assertions.assertThat(group.convert2FullColumnName("name")).isEqualTo("subBean[0].name");
        //when
        String idFullName = group.convert2FullColumnName("sid");
        String nameFullName = group.convert2FullColumnName("name");
        //then
        Assertions.assertThat(idFullName).isEqualTo("subBean[1].sid");
        Assertions.assertThat(nameFullName).isEqualTo("subBean[1].name");

    }

    @Test
    public void should_get_full_name_by_index_order() {
        ColumnGroup group = new ColumnGroup("subBean");
        group.addColumnName("sid","name");
        //when then
        Assertions.assertThat(group.convert2FullColumnName("sid")).isEqualTo("subBean[0].sid");
        Assertions.assertThat(group.convert2FullColumnName("sid")).isEqualTo("subBean[1].sid");
        Assertions.assertThat(group.convert2FullColumnName("name")).isEqualTo("subBean[0].name");

    }
}