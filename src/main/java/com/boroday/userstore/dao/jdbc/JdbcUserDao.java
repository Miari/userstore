package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.dao.jdbc.mapper.UserRowMapper;
import com.boroday.userstore.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class JdbcUserDao implements UserDao {

    private static final String GET_ALL_USERS = "select id, login, firstName, lastName, salary, dateOfBirth, password from Users order by id";
    private static final String ADD_NEW_USER = "insert into Users (firstName, lastName, salary, dateOfBirth, login, password) values (?,?,?,?,?,?)";
    private static final String REMOVE_USER = "delete from Users where id = ?";
    private static final String SELECT_USER_BY_ID = "select id, login, password, firstName, lastName, salary, dateOfBirth from Users where id = ?";
    private static final String SELECT_USER_BY_LOGIN = "select id, login, password, firstName, lastName, salary, dateOfBirth from Users where login like ?";
    private static final String UPDATE_USER = "update Users set firstName = ?, lastName = ?, salary = ?, dateOfBirth = ?, login = ?, password = ? where id = ?";
    private static final String SEARCH_USER = "select id, firstName, lastName, salary, dateOfBirth, login, password from Users where lower(firstName) like lower(?) or lower(lastName) like lower (?) or lower(login) like lower(?) order by id";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private DataSource dataSource;

    public JdbcUserDao() {
    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public List<User> getAll() {
        log.info("Start to get all users from DB");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<User> listOfUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                listOfUsers.add(user);
            }

            log.debug("{} users were get from DB", listOfUsers.size());
            return listOfUsers;
        } catch (SQLException ex) {
            log.error("Not possible to get all users", ex);
            throw new RuntimeException("It is not possible to show all users", ex);
        }
    }

    public void addNewUser(User user) {
        log.info("Start to add new user to DB");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDouble(3, user.getSalary());
            Timestamp timestamp = Timestamp.valueOf(user.getDateOfBirth().atStartOfDay());
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();

            log.debug("New user with name \"{} {}\" was added to DB", user.getFirstName(), user.getLastName());
        } catch (SQLException ex) {
            log.error("Not possible to add new user with name \"{} {}\"", user.getFirstName(), user.getLastName(), ex);
            throw new RuntimeException("It is not possible to add new user", ex);
        }
    }

    public void removeUser(long userId) {
        log.info("Start to remove user from DB");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)) {

            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();

            log.debug("User with id {} was removed", userId);
        } catch (SQLException ex) {
            log.error("Not possible to remove user by id {}", userId, ex);
            throw new RuntimeException("It is not possible to remove user with ID " + userId, ex);
        }
    }

    public User getUserById(long userId) {
        log.info("Start to get user from DB by id");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {

            preparedStatement.setLong(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = USER_ROW_MAPPER.mapRow(resultSet);
                    log.debug("User with id {} was got", userId);
                    return user;
                } else {
                    log.info("User with id {} does not exist in DB", userId);
                    return null;
                }
            }
        } catch (SQLException ex) {
            log.error("Not possible to get user by id {}", userId, ex);
            throw new RuntimeException("It is not possible to get user by ID " + userId, ex);
        }
    }

    public User getUserByLogin(String userLogin, String userPassword) {
        log.info("Start to get user from DB by login");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, userLogin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = USER_ROW_MAPPER.mapRow(resultSet);
                    if (user.getPassword().equals(userPassword)) {
                        log.debug("User with login {} was got", userLogin);
                        return user;
                    } else {
                        log.info("Password for User with login {} is incorrect", userLogin);
                        return null;
                    }
                } else {
                    log.info("There is no User with login {} in DB", userLogin);
                    return null;
                }
            }
        } catch (SQLException ex) {
            log.error("Not possible to get user by login {}", userLogin, ex);
            throw new RuntimeException("It is not possible to get user by login" + userLogin, ex);
        }
    }

    public void updateUser(User user) {
        log.info("Start to update user in DB");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDouble(3, user.getSalary());
            Timestamp timestamp = Timestamp.valueOf(user.getDateOfBirth().atStartOfDay());
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setLong(7, user.getId());
            preparedStatement.executeUpdate();

            log.debug("User with id {} was updated", user.getId());
        } catch (SQLException ex) {
            log.error("not possible to update user with id {}", user.getId(), ex);
            throw new RuntimeException("It is not possible to update user with ID " + user.getId(), ex);
        }
    }

    public List<User> searchUser(String text) {
        log.info("Start to search user in DB");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_USER)) {

            List<User> listOfUsers = new ArrayList<>();
            preparedStatement.setString(1, "%" + text + "%");
            preparedStatement.setString(2, "%" + text + "%");
            preparedStatement.setString(3, "%" + text + "%");
            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    User user = USER_ROW_MAPPER.mapRow(resultSet);
                    listOfUsers.add(user);
                }
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }

            log.debug("{} users were found by text: {}", listOfUsers.size(), text);
            return listOfUsers;
        } catch (
                SQLException ex) {
            log.error("Not possible to search users by text: {}", text, ex);
            throw new RuntimeException("It is not possible to search users by text " + text, ex);
        }
    }
}
