package org.springframework.batch.item.excel.mapping;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.batch.item.Player;
import org.springframework.batch.item.excel.MockSheet;
import org.springframework.batch.item.excel.support.rowset.DefaultRowSetFactory;
import org.springframework.batch.item.excel.support.rowset.RowSet;
import org.springframework.beans.factory.config.DeprecatedBeanWarner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author Marten Deinum
 * @since 0.5.0
 */
public class BeanWrapperRowMapperTest {

    @Test(expected = IllegalStateException.class)
    public void givenNoNameWhenInitCompleteThenIllegalStateShouldOccur() throws Exception {
        BeanWrapperRowMapper mapper = new BeanWrapperRowMapper();
        mapper.afterPropertiesSet();
    }

    public static class TestBean {
        private String id;
        ArrayList<SubTestBean> subBean;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ArrayList<SubTestBean> getSubBean() {
            return subBean;
        }

        public void setSubBean(ArrayList<SubTestBean> subBean) {
            this.subBean = subBean;
        }
    }
    public static class SubTestBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void convertDependentOfBeanTest() throws BindException {
        BeanWrapperRowMapper<TestBean> mapper = new BeanWrapperRowMapper<>();
        mapper.setTargetType(TestBean.class);
        mapper.setStrict(false);
        mapper.setDistanceLimit(1);

        Properties properties = new Properties();
        properties.put("id","0");
        properties.put("subBean.id", "0-1");
        properties.put("subBean.name", "my name");

        RowSet rowSet = Mockito.mock(RowSet.class);

        Mockito.when(rowSet.getProperties()).thenReturn(properties);
        TestBean bean =  mapper.mapRow(rowSet);

        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.id).isEqualTo("0");

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
