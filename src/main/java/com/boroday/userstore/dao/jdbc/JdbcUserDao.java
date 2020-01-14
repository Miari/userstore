package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.jdbc.mapper.UserRowMapper;
import com.boroday.userstore.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao {

    private static final String GET_ALL_SQL = "select * from users order by id";
    private static final String ADD_NEW_USER = "insert into users (firstName, lastName, salary, dateOfBirth) values (?,?,?,?)";
    private static final String REMOVE_USER = "delete from users where id=(?)";
    private static final String SELECT_USER_BY_ID = "select * from users where id=(?)";
    private String update_user = "update users set firstName = ?, lastName = ?, salary = ";
    private static final String SEARCH_USER = "select * from users where lower(firstName) like lower(?) or lower(lastName) like lower (?) order by id";

    public List<User> getAll() {
        List<User> listOfUsers = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_ALL_SQL)
        ) {
            while (resultSet.next()) {
                UserRowMapper userRowMapper = new UserRowMapper();
                User user = userRowMapper.mapRow(resultSet);
                listOfUsers.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listOfUsers;
    }

    public int addNewUser(User user) {
        int countOfInsertedUsers = 0;
        try (
                Connection connection = getConnection();
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
            ex.printStackTrace();
        }
        return countOfInsertedUsers;
    }

    public int removeUser(int userId) {
        int countOfRemovedUsers = 0;
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)
        ) {
            preparedStatement.setInt(1, userId);
            countOfRemovedUsers = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countOfRemovedUsers;
    }

    public User getUserById(int userId) {
        User user = new User();
        UserRowMapper userRowMapper = new UserRowMapper();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)
        ) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public int updateUser(User user) {
        int countOfUpdatedUsers = 0;
        int numberOfParameter = 4;
        if(user.getSalary()==null) {
            update_user += "null, dateOfBirth = ? where id = ?";
            numberOfParameter = 3;
        } else {
            update_user += "?, dateOfBirth = ? where id = ?";
        }
        System.out.println("here "+update_user);
        try (
                Connection connection = getConnection();
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
            preparedStatement.setInt(numberOfParameter+1, user.getId());

            countOfUpdatedUsers = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countOfUpdatedUsers;
    }

    public List<User> searchUser(String text) {

        List<User> listOfUsers = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER)
        ) {
            preparedStatement.setString(1, "%"+text+"%");
            preparedStatement.setString(2, "%"+text+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserRowMapper userRowMapper = new UserRowMapper();
                User user = userRowMapper.mapRow(resultSet);
                listOfUsers.add(user);
            }
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return listOfUsers;
    }

    private Connection getConnection() throws SQLException {
        String host = "jdbc:postgresql://localhost:5432/userstore";
        String username = "postgres";
        String password = "12345";

        return DriverManager.getConnection(host, username, password);
    }
}
