package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.dao.jdbc.mapper.UserRowMapper;
import com.boroday.userstore.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

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

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll() {
        log.info("Getting all users from DB");
        return jdbcTemplate.query(GET_ALL_USERS, USER_ROW_MAPPER);
    }

    public void addNewUser(User user) {
        log.info("Adding a new user to DB");
        Timestamp timestamp = Timestamp.valueOf(user.getDateOfBirth().atStartOfDay());
        Object[] args = new Object[]{user.getFirstName(),
                user.getLastName(),
                user.getSalary(),
                timestamp,
                user.getLogin(),
                user.getPassword()};
        try {
            jdbcTemplate.update(ADD_NEW_USER, args);
        } catch (Exception ex) {
            log.error("Not possible to add new user with name \"{} {}\"", user.getFirstName(), user.getLastName(), ex);
        }
    }

    public void removeUser(long userId) {
        log.info("Removing user from DB");
        Object[] args = new Object[]{userId};
        try {
            jdbcTemplate.update(REMOVE_USER, args);
        } catch (Exception ex) {
            log.error("Not possible to remove user by id {}", userId, ex);
        }
    }

    public User getUserById(long userId) {
        log.info("Getting user from DB by id");
        Object[] args = new Object[]{userId};
        List<User> listOfUsers = jdbcTemplate.query(SELECT_USER_BY_ID, args, USER_ROW_MAPPER);
        if (listOfUsers.size() > 0) {
            return listOfUsers.get(0);
        } else {
            log.info("User with id {} does not exist in DB", userId);
            return null;
        }
    }

    public User getUserByLogin(String userLogin, String userPassword) {
        log.info("Getting user from DB by login");
        Object[] args = new Object[]{userLogin};
        List<User> listOfUsers = jdbcTemplate.query(SELECT_USER_BY_LOGIN, args, USER_ROW_MAPPER);
        if (listOfUsers.size() > 0) {
            User user = listOfUsers.get(0);
            if (user.getPassword().equals(userPassword)) {
                return listOfUsers.get(0);
            } else {
                log.info("Password for User with login {} is incorrect", userLogin);
                return null;
            }
        } else {
            log.info("User with login {} does not exist in DB", userLogin);
            return null;
        }
    }

    public void updateUser(User user) {
        log.info("Updating user in DB");
        Timestamp timestamp = Timestamp.valueOf(user.getDateOfBirth().atStartOfDay());
        Object[] args = new Object[]{user.getFirstName(),
                user.getLastName(),
                user.getSalary(),
                timestamp,
                user.getLogin(),
                user.getPassword(),
                user.getId()};
        try {
            jdbcTemplate.update(UPDATE_USER, args);
        } catch (Exception ex) {
            log.error("not possible to update user with id {}", user.getId(), ex);
        }
    }

    public List<User> searchUser(String text) {
        log.info("Searching user in DB");
        String searchMask = "%" + text + "%";
        Object[] args = new Object[]{searchMask, searchMask, searchMask};
        return jdbcTemplate.query(SEARCH_USER, args, USER_ROW_MAPPER);
    }
}
