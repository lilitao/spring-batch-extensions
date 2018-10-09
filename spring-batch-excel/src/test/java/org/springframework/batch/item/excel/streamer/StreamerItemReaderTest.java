package org.springframework.batch.item.excel.streamer;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.UploadBean;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.mapping.PassThroughRowMapper;
import org.springframework.batch.item.excel.support.rowset.DefaultRowSetFactory;
import org.springframework.batch.item.excel.support.rowset.RowNumberColumnNameExtractor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class StreamerItemReaderTest {
    protected final Log logger = LogFactory.getLog(getClass());
    StreamerItemReader<ExcelBean> reader;

  //  @Before
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
    public void should_retrieve_object_given_2_header_column() throws Exception {
        StreamerItemReader<UploadBean> reader = new StreamerItemReader<>();
        reader.setResource(new ClassPathResource("/org/springframework/batch/item/excel/0000PL0026_upload template.xlsx"));
        BeanWrapperRowMapper<UploadBean> rowMapper = new BeanWrapperRowMapper<>();

        DefaultRowSetFactory rowSetFactory = new DefaultRowSetFactory();
        RowNumberColumnNameExtractor columnNameExtractor = new RowNumberColumnNameExtractor();

        columnNameExtractor.setRowNumberOfColumnNames(1);

        rowSetFactory.setColumnNameExtractor(columnNameExtractor);
        reader.setRowSetFactory(rowSetFactory);

        rowMapper.setTargetType(UploadBean.class);
        rowMapper.setDistanceLimit(1);
        reader.setRowMapper(rowMapper);

        reader.setLinesToSkip(2);

        reader.open(new ExecutionContext());

        UploadBean bean = reader.read();

        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getCertNo()).isEqualTo("0000000001");
        Assertions.assertThat(bean.getRowNumber()).isEqualTo(2);
    }

    @Test
    public void should_extract_tow_row_from_excel() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/org/springframework/batch/item/excel/0000PL0026_upload template.xlsx");
        Workbook workbook = StreamingReader.builder().open(classPathResource.getInputStream());
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();

        //Row first = rows.next();

        Row second = rows.next();

        Assertions.assertThat(second.getRowNum()).isEqualTo(1);
        Assertions.assertThat(sheet.getLastRowNum()).isEqualTo(21);
        Assertions.assertThat(second.getCell(0).getStringCellValue()).isEqualTo("Client");
    }

    @Test
    public void should_retrieve_code_given_first_row_not_blank() throws Exception {
        StreamerItemReader<String[]> reader = new StreamerItemReader<>();
        reader.setResource(new ClassPathResource("/org/springframework/batch/item/excel/0823100429_1000.xlsx"));
        reader.setRowMapper(new PassThroughRowMapper());
        reader.open(new ExecutionContext());


        String[] firstRow = reader.read();

        Assertions.assertThat(Arrays.stream(firstRow).distinct().toArray()).containsExactly("","20110","21103","20120","31201","31400");
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