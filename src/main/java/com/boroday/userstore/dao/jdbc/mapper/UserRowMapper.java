package com.boroday.userstore.dao.jdbc.mapper;

import com.boroday.userstore.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));


        Double salary = resultSet.getDouble("salary");
        if (salary != null) {
            user.setSalary(salary);
        }

        Date dateOfBirth = resultSet.getDate("dateOfBirth");
        if (dateOfBirth != null) {
            user.setDateOfBirth(dateOfBirth.toLocalDate());
        }

        return user;
    }
}
