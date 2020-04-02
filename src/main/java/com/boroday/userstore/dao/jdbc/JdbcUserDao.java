package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.dao.jdbc.mapper.UserRowMapper;
import com.boroday.userstore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private static final String GET_ALL_USERS = "select id, firstName, lastName, salary, dateOfBirth from users order by id";
    private static final String ADD_NEW_USER = "insert into users (firstName, lastName, salary, dateOfBirth) values (?,?,?,?)";
    private static final String REMOVE_USER = "delete from users where id = ?";
    private static final String SELECT_USER_BY_ID = "select id, firstName, lastName, salary, dateOfBirth from users where id = ?";
    private static final String UPDATE_USER = "update users set firstName = ?, lastName = ?, salary = ?, dateOfBirth = ? where id = ?";
    private static final String SEARCH_USER = "select id, firstName, lastName, salary, dateOfBirth from users where lower(firstName) like lower(?) or lower(lastName) like lower (?) order by id";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private DataSource dataSource;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> getAll() {
        log.info("Trying to get all users from DB");
        List<User> listOfUsers = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)
        ) {
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                listOfUsers.add(user);
            }
            log.info("All users were get from DB");
            log.debug("{} users were get from DB", listOfUsers.size());
        } catch (SQLException ex) {
            log.error("Not possible to get all users");
            throw new RuntimeException("Connection to database is not available. It is not possible to show all users", ex);
        }

        return listOfUsers;
    }

    public void addNewUser(User user) {
        log.info("Trying to get new user to DB");
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.executeUpdate();
            log.info("New user was added to DB");
            log.debug("New user with name \"{}\" \"{}\" was added to DB", user.getFirstName(), user.getLastName());
        } catch (SQLException ex) {
            log.error("Not possible to add new user with name \"{}\" \"{}\"", user.getFirstName(), user.getLastName());
            throw new RuntimeException("Connection to database is not available. It is not possible to add new user", ex);
        }
    }

    public void removeUser(long userId) {
        log.info("Trying to remove user from DB");
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER)
        ) {
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
            log.info("User was removed");
            log.debug("User with id {} was removed", userId);
        } catch (SQLException ex) {
            log.error("Not possible to remove user by id {}", userId);
            throw new RuntimeException("Connection to database is not available. It is not possible to remove user with ID " + userId, ex);
        }
    }

    public User getUserById(long userId) {
        log.info("Trying to get user from DB by id");
        User user = new User();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)
        ) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = USER_ROW_MAPPER.mapRow(resultSet);
            }
            resultSet.close();
            log.info("Users was gotten");
            log.debug("User with id {} was gotten", userId);
        } catch (SQLException ex) {
            log.error("Not possible to get user by id {}", userId);
            throw new RuntimeException("Connection to database is not available. It is not possible to get user by ID " + userId, ex);
        }
        return user;
    }

    public void updateUser(User user) {
        log.info("Trying to update user in DB");
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)
        ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDouble(3, user.getSalary());
            preparedStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();
            log.info("User was updated");
            log.debug("User with id {} was updated", user.getId());
        } catch (SQLException ex) {
            log.error("not possible to update user with id {}", user.getId());
            throw new RuntimeException("Connection to database is not available. It is not possible to update user with ID " + user.getId(), ex);
        }
    }

    public List<User> searchUser(String text) {
        log.info("Trying to search user in DB");
        List<User> listOfUsers = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
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
            log.info("Search of users completed");
            log.debug("{} users were found by text: {}", listOfUsers.size(), text);
        } catch (
                SQLException ex) {
            log.error("Not possible to search users by text: {}", text);
            throw new RuntimeException("Connection to database is not available . It is not possible to search users by text " + text, ex);
        }
        return listOfUsers;
    }

}
