package com.boroday.userstore.dao.jdbc.mapper;

import com.boroday.userstore.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet resultSet) throws SQLException {
        return mapRow(resultSet, 0);
    }

    public User mapRow(ResultSet resultSet, int countOfRecords) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setSalary(resultSet.getDouble("salary"));

        Timestamp dateOfBirth = resultSet.getTimestamp("dateOfBirth");
        user.setDateOfBirth(dateOfBirth.toLocalDateTime().toLocalDate());

        return user;
    }
}
