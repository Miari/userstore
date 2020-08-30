package com.boroday.userstore.context;

import com.boroday.ioc.context.ApplicationContext;
import com.boroday.ioc.context.ClassPathApplicationContext;
import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;

public class BeanCreationITest {

    @Test
    public void testCreateDataSourceBean() {
        //prepare
        ApplicationContext applicationContextDataSource = new ClassPathApplicationContext(new String[]{"src/test/resources/test-context-data-source.xml"});

        //when
        BasicDataSource dataSource = (BasicDataSource) applicationContextDataSource.getBean("dataSource");

        //then
        assertEquals("jdbc:postgresql://localhost:5432/userstore", dataSource.getUrl());
        assertEquals("postgres", dataSource.getUsername());
        assertEquals ("root", dataSource.getPassword());
    }

    @Test
    public void testCreateUserDaoBean() {
        //prepare
        ApplicationContext applicationContextUserDao = new ClassPathApplicationContext(new String[]{"src/test/resources/test-context-user-dao.xml"});

        //when
         JdbcUserDao userDao = (JdbcUserDao) applicationContextUserDao.getBean("userDao");

        //then
        assertEquals("jdbc:postgresql://localhost:5432/userstore", (((BasicDataSource) userDao.getDataSource()).getUrl()));
        assertEquals("postgres", (((BasicDataSource) userDao.getDataSource()).getUsername()));
        assertEquals("root", (((BasicDataSource) userDao.getDataSource()).getPassword()));
    }


}
