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
    private String update_user = "update users set firstName = ?, lastName = ?, salary = ";
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
            throw new RuntimeException("Connection to database is not available. It is not possible to show all users with query: " + GET_ALL_USERS, ex);
        }

        return listOfUsers;
    }

    public int addNewUser(User user) {
        int countOfInsertedUsers;
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER)
        ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            if (user.getSalary() != null) {
                preparedStatement.setDouble(3, user.getSalary());
            } else {
                preparedStatement.setObject(3, null);
            }
            if (user.getDateOfBirth() != null) {
                preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            } else {
                preparedStatement.setNull(4, Types.DATE);
            }

            countOfInsertedUsers = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to add user with query: " + ADD_NEW_USER, ex);
        }
        return countOfInsertedUsers;
    }

    public int removeUser(long userId) {
        int countOfRemovedUsers;
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)
        ) {
            preparedStatement.setLong(1, userId);
            countOfRemovedUsers = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to remove user with query: " + REMOVE_USER, ex);
        }
        return countOfRemovedUsers;
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
            throw new RuntimeException("Connection to database is not available. It is not possible to get user by ID with query: " + SELECT_USER_BY_ID, ex);
        }
        return user;
    }

    public int updateUser(User user) {
        int countOfUpdatedUsers;
        int numberOfParameter = 4;
        if (user.getSalary() == null) {
            update_user += "null, dateOfBirth = ? where id = ?";
            numberOfParameter = 3;
        } else {
            update_user += "?, dateOfBirth = ? where id = ?";
        }

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(update_user)
        ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());

            if (user.getSalary() != null) {
                preparedStatement.setDouble(3, user.getSalary());
            }

            if (user.getDateOfBirth() != null) {
                preparedStatement.setDate(numberOfParameter, Date.valueOf(user.getDateOfBirth()));
            } else {
                preparedStatement.setNull(numberOfParameter, Types.DATE);
            }
            preparedStatement.setLong(numberOfParameter + 1, user.getId());

            countOfUpdatedUsers = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Connection to database is not available. It is not possible to update user with query: " + update_user, ex);
        }
        return countOfUpdatedUsers;
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
            throw new RuntimeException("Connection to database is not available . It is not possible to search users by text " + text + " with query: " + SEARCH_USER, ex);
        }
        return listOfUsers;
    }

}
