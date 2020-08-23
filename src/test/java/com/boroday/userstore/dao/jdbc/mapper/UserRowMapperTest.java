package com.boroday.userstore.dao.jdbc.mapper;

import com.boroday.userstore.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong("id")).thenReturn((long) 1);
        when(mockResultSet.getString("login")).thenReturn("len");
        when(mockResultSet.getString("password")).thenReturn("789");
        when(mockResultSet.getString("firstName")).thenReturn("Lena");
        when(mockResultSet.getString("lastName")).thenReturn("Strim");
        when(mockResultSet.getDouble("salary")).thenReturn(1500.00);

        LocalDate localDate = LocalDate.of(2000, Month.MAY, 21);
        Date date = Date.valueOf(localDate);

        Timestamp timestamp = new Timestamp(date.getTime());
        when(mockResultSet.getTimestamp("dateOfBirth")).thenReturn(timestamp);

        User testUser = userRowMapper.mapRow(mockResultSet);

        assertNotNull(testUser);
        assertEquals(1, testUser.getId());
        assertEquals("len", testUser.getLogin());
        assertEquals("789", testUser.getPassword());
        assertEquals("Lena", testUser.getFirstName());
        assertEquals("Strim", testUser.getLastName());
        assertEquals(1500.00, testUser.getSalary(), 0);
        assertEquals(localDate, testUser.getDateOfBirth());
    }
}
