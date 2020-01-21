package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.jdbc.mapper.UserRowMapper;
import com.boroday.userstore.dao.ConnectionFactory;
import com.boroday.userstore.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao {

    private static final String GET_ALL_USERS = "select id, firstName, lastName, salary, dateOfBirth from users order by id";
    private static final String ADD_NEW_USER = "insert into users (firstName, lastName, salary, dateOfBirth) values (?,?,?,?)";
    private static final String REMOVE_USER = "delete from users where id = ?";
    private static final String SELECT_USER_BY_ID = "select id, firstName, lastName, salary, dateOfBirth from users where id = ?";
    private static final String UPDATE_USER = "update users set firstName = ?, lastName = ?, salary = ?, dateOfBirth = ? where id = ?";
    private static final String SEARCH_USER = "select id, firstName, lastName, salary, dateOfBirth from users where lower(firstName) like lower(?) or lower(lastName) like lower (?) order by id";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    public List<User> getAll() {
        List<User> listOfUsers = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)
        ) {
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                listOfUsers.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to show all users", ex);
        }

        return listOfUsers;
    }

    public void addNewUser(User user) {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to add new user", ex);
        }
    }

    public void removeUser(long userId) {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)
        ) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to remove user with ID " + userId, ex);
        }
    }

    public User getUserById(long userId) {
        User user = new User();
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)
        ) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = USER_ROW_MAPPER.mapRow(resultSet);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to get user by ID " + userId, ex);
        }
        return user;
    }

    public void updateUser(User user) {
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)
        ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to update user with ID " + user.getId(), ex);
        }
    }

    public List<User> searchUser(String text) {

        List<User> listOfUsers = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER)
        ) {
            preparedStatement.setString(1, "%" + text + "%");
            preparedStatement.setString(2, "%" + text + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                listOfUsers.add(user);
            }
            resultSet.close();
        } catch (
                SQLException ex) {
            throw new RuntimeException("Connection to database is not available . It is not possible to search users by text " + text, ex);
        }
        return listOfUsers;
    }

}
