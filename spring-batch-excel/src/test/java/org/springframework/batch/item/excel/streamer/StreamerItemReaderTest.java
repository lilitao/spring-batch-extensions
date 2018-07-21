package org.springframework.batch.item.excel.streamer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.core.io.ClassPathResource;

import static org.junit.Assert.*;

public class StreamerItemReaderTest {

    StreamerItemReader<ExcelBean> reader;

    //@Before
    public void setUp() {
        reader = new StreamerItemReader<>(0);
        reader.setResource(new ClassPathResource("/org/springframework/batch/item/excel/player.xlsx"));
        reader.setRowMapper(new BeanWrapperRowMapper<ExcelBean>());
        reader.open(new ExecutionContext());

    }

    @Test
    public void should_open_sheet() {
        // Sheet   reader.getSheet(0);
    }

    private class ExcelBean {
        private String id;
        private String lastName;
        private String firstName;
        private String position;
        private String birthYear;
        private String debutYear;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(String birthYear) {
            this.birthYear = birthYear;
        }

        public String getDebutYear() {
            return debutYear;
        }

        public void setDebutYear(String debutYear) {
            this.debutYear = debutYear;
        }
    }
}