package org.springframework.batch.item.excel.streamer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.support.rowset.DefaultRowSetFactory;
import org.springframework.batch.item.excel.support.rowset.RowNumberColumnNameExtractor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public class StreamerItemReaderTest {
    protected final Log logger = LogFactory.getLog(getClass());
    StreamerItemReader<ExcelBean> reader;

    @Before
    public void setUp() {
        reader = new StreamerItemReader<>();
        reader.setResource(new ClassPathResource("/org/springframework/batch/item/excel/player.xlsx"));
        BeanWrapperRowMapper<ExcelBean> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setTargetType(ExcelBean.class);
        reader.setRowMapper(rowMapper);
        reader.setLinesToSkip(1);
        reader.open(new ExecutionContext());

    }

    @Test
    public void should_open_sheet() {
        //when
        Sheet sheet = reader.getSheet(0);
        Assertions.assertThat(sheet).isNotNull();
    }

    @Test
    public void should_get_data_array_from_sheet() {
        Sheet sheet = reader.getSheet(0);
        String[] data = null;
        data = sheet.getRow(0);
        logger.info(StringUtils.arrayToCommaDelimitedString(data));
        Assertions.assertThat(data.length).isEqualTo(6);
        Assertions.assertThat(data).containsExactly("AbduKa00", "Abdul-Jabbar", "Karim", "rb", "1974.0", "1996.0");
    }

    @Test
    public void should_get_data_from_excel() throws Exception {
        ExcelBean data = null;
        int i = 0;
        data = reader.read();
        logger.info(data);
        Assertions.assertThat(data.toString()).contains("AbduKa00","Abdul-Jabbar","Karim","rb","1974.0","1996.0");
    }

    @Test
    public void shortBeanTest() throws Exception {
        StreamerItemReader<ExcelBeanShort> reader = new StreamerItemReader<>();
        reader.setResource(new ClassPathResource("/org/springframework/batch/item/excel/player.xlsx"));
        BeanWrapperRowMapper<ExcelBeanShort> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setStrict(false);
        rowMapper.setTargetType(ExcelBeanShort.class);
        reader.setRowMapper(rowMapper);
        reader.setLinesToSkip(1);

        DefaultRowSetFactory rowSetFactory = new DefaultRowSetFactory();
        RowNumberColumnNameExtractor columnNameExtractor = new RowNumberColumnNameExtractor();

        rowSetFactory.setColumnNameExtractor(columnNameExtractor);
        reader.setRowSetFactory(rowSetFactory);

        reader.open(new ExecutionContext());

        Sheet sheet = reader.getSheet(0);
        ExcelBeanShort data = null;
        data = reader.read();
        logger.info(data);
        Assertions.assertThat(data.toString()).contains( "rb");
    }

    @Test
    public void shortBeanHeaderForm2Test() throws Exception {
        StreamerItemReader<ExcelBeanShort> reader = new StreamerItemReader<>();
        reader.setResource(new ClassPathResource("/org/springframework/batch/item/excel/player-header.xlsx"));
        BeanWrapperRowMapper<ExcelBeanShort> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setStrict(false);
        rowMapper.setTargetType(ExcelBeanShort.class);
        reader.setRowMapper(rowMapper);
        reader.setLinesToSkip(2);


        DefaultRowSetFactory rowSetFactory = new DefaultRowSetFactory();
        RowNumberColumnNameExtractor columnNameExtractor = new RowNumberColumnNameExtractor();
        columnNameExtractor.setRowNumberOfColumnNames(1);
        rowSetFactory.setColumnNameExtractor(columnNameExtractor);
        reader.setRowSetFactory(rowSetFactory);

        reader.open(new ExecutionContext());

        Sheet sheet = reader.getSheet(0);
        ExcelBeanShort data = null;
        data = reader.read();
        logger.info(data);
        Assertions.assertThat(data).isNotNull();
    }

    public static class ExcelBeanShort {
        private String id;
        private String lastName;
        private String firstName;
        private String position;

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

        @Override
        public String toString() {
            return "ExcelBeanShort{" +
                    "id='" + id + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", position='" + position + '\'' +
                    '}';
        }
    }




    public static class ExcelBean {
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

        @Override
        public String toString() {
            return "ExcelBean{" +
                    "id='" + id + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", position='" + position + '\'' +
                    ", birthYear='" + birthYear + '\'' +
                    ", debutYear='" + debutYear + '\'' +
                    '}';
        }
    }



}