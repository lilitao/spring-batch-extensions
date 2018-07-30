package org.springframework.batch.item.excel.mapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.batch.item.Player;
import org.springframework.batch.item.excel.MockSheet;
import org.springframework.batch.item.excel.Sheet;
import org.springframework.batch.item.excel.support.rowset.ColumnGroup;
import org.springframework.batch.item.excel.support.rowset.DefaultRowSetFactory;
import org.springframework.batch.item.excel.support.rowset.RowNumberColumnNameExtractor;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Marten Deinum
 * @since 0.5.0
 */
public class BeanWrapperRowMapperTest {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Test(expected = IllegalStateException.class)
    public void givenNoNameWhenInitCompleteThenIllegalStateShouldOccur() throws Exception {
        BeanWrapperRowMapper mapper = new BeanWrapperRowMapper();
        mapper.afterPropertiesSet();
    }

    @Test
    public void convertDependentOfBeanTest() throws BindException {
        BeanWrapperRowMapper<TestWarpBean> mapper = new BeanWrapperRowMapper<>();
        mapper.setTargetType(TestWarpBean.class);
        mapper.setStrict(false);
        mapper.setDistanceLimit(1);

        RowData properties = new RowData();
        properties.addColumn("id","0");
        properties.addColumn("subBean[0].sid", "0-1");
        properties.addColumn("subBean[0].name", "my name");
        properties.addColumn("subBean[1].sid", "0-2");
        properties.addColumn("subBean[1].name", "my name 2");

        RowSet rowSet = mock(RowSet.class);

        when(rowSet.getProperties()).thenReturn(properties);
        TestWarpBean bean =  mapper.mapRow(rowSet);

        logger.info("result:{}"+ bean);
        System.out.println(bean);

        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getId()).isEqualTo("0");

    }

    @Test
    public void should_convert_nested_property_bean_given_column_name() throws BindException {
        BeanWrapperRowMapper<TestWarpBean> mapper = new BeanWrapperRowMapper<>();
        mapper.setTargetType(TestWarpBean.class);
        mapper.setStrict(false);
        mapper.setDistanceLimit(0);

        DefaultRowSetFactory factory = new DefaultRowSetFactory();
        ColumnGroup subBean = new ColumnGroup("subBean");
        subBean.addColumnName("sid", "name");
        mapper.addColumnGroup(subBean);


        Sheet sheet = mock(Sheet.class);
        when(sheet.getRow(0)).thenReturn(new String[]{"id", "sid", "name", "sid", "name"});
        when(sheet.getRow(1)).thenReturn(new String[]{"1", "1-1", "name-1-1", "1-2", "name-1-2"});
        when(sheet.getNumberOfRows()).thenReturn(2);
        RowSet rowSet = factory.create(sheet);

        rowSet.next();
        rowSet.next();

        TestWarpBean bean = mapper.mapRow(rowSet);
        logger.info(bean);
        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getId()).isEqualTo("1");
        Assertions.assertThat(bean.getSubBean().get(0).getSid()).isEqualTo("1-1");
        Assertions.assertThat(bean.toString()).contains("name-1-2","name-1-1","1-2");


    }

    @Test
    public void typeTest() {
        String string = "java.util.List<org.springframework.batch.item.excel.mapping.SubTestBean> ";
        logger.info(string.substring(string.indexOf("<")+1,string.indexOf(">")));
    }

    @Test
    public void givenAValidRowWhenMappingThenAValidPlayerShouldBeConstructed() throws Exception {
        BeanWrapperRowMapper<Player> mapper = new BeanWrapperRowMapper<Player>();
        mapper.setTargetType(Player.class);
        mapper.afterPropertiesSet();

        List<String[]> rows = new ArrayList<String[]>();
        rows.add(new String[]{"id", "lastName", "firstName", "position", "birthYear", "debutYear"});
        rows.add( new String[]{"AbduKa00", "Abdul-Jabbar", "Karim", "rb", "1974", "1996"});
        MockSheet sheet = new MockSheet("players", rows);


        RowSet rs = new DefaultRowSetFactory().create(sheet);
        rs.next();
        rs.next();

        Player p = mapper.mapRow(rs);
        assertNotNull(p);
        assertEquals("AbduKa00", p.getId());
        assertEquals("Abdul-Jabbar", p.getLastName());
        assertEquals("Karim", p.getFirstName());
        assertEquals("rb", p.getPosition());
        assertEquals(1974, p.getBirthYear());
        assertEquals(1996, p.getDebutYear());
        assertNull(p.getComment());

    }

    @Test
    public void givenAValidRowWhenMappingThenAValidPlayerShouldBeConstructedBasedOnPrototype() throws Exception {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class);
        BeanWrapperRowMapper<Player> mapper = new BeanWrapperRowMapper<Player>();
        mapper.setPrototypeBeanName("player");
        mapper.setBeanFactory(ctx);
        mapper.afterPropertiesSet();

        List<String[]> rows = new ArrayList<String[]>();
        rows.add(new String[]{"id", "lastName", "firstName", "position", "birthYear", "debutYear"});
        rows.add( new String[]{"AbduKa00", "Abdul-Jabbar", "Karim", "rb", "1974", "1996"});
        MockSheet sheet = new MockSheet("players", rows);

        RowSet rs = new DefaultRowSetFactory().create(sheet);
        rs.next();
        rs.next();
        Player p = mapper.mapRow(rs);

        assertNotNull(p);
        assertEquals("AbduKa00", p.getId());
        assertEquals("Abdul-Jabbar", p.getLastName());
        assertEquals("Karim", p.getFirstName());
        assertEquals("rb", p.getPosition());
        assertEquals(1974, p.getBirthYear());
        assertEquals(1996, p.getDebutYear());
        assertEquals("comment from context", p.getComment());
    }

    @Configuration
    public static class TestConfig {

        @Bean
        @Scope(value = "prototype")
        public Player player() {
            Player p = new Player();
            p.setComment("comment from context");
            return p;
        }

    }
}
