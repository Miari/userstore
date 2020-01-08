package com.boroday.userstore.database;

import com.boroday.userstore.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteQuery {

    private static final String ADD_NEW_USER = "insert into users values (?,?,?,?)";

    public Map<String, Object> getAllUsers(String query) throws SQLException {

        Map<String, Object> map = new HashMap<>();
        List<User> listOfUsers = new ArrayList<>();

        DatabaseConnection dbConnection = new DatabaseConnection();
        Statement statement = dbConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setSalary(resultSet.getDouble("salary"));
            /*if (resultSet.getDate("dateOfBirth") != null) {
                user.setDateOfBirth(resultSet.getDate("dateOfBirth").toLocalDate());
            }*/
            listOfUsers.add(user);
        }
        map.put("users", listOfUsers);
        return map;
    }

    public static int insertUser(HttpServletRequest request) throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(ADD_NEW_USER);
        preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
        preparedStatement.setString(2, request.getParameter("firstName"));
        preparedStatement.setString(3, request.getParameter("lastName"));
        preparedStatement.setDouble(4, Double.parseDouble(request.getParameter("salary")));
        return preparedStatement.executeUpdate();
    }
}
