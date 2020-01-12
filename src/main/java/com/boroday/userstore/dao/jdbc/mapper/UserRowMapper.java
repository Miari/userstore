package com.boroday.userstore.dao.jdbc.mapper;

import com.boroday.userstore.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    public User mapRow (ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setSalary(resultSet.getDouble("salary"));
        Date date = resultSet.getDate("dateOfBirth");
            if (date != null) {
                user.setDateOfBirth(date.toLocalDate());
            }
        return user;
    }
}
