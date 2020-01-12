package com.boroday.userstore.dao.jdbc;

import com.boroday.userstore.dao.jdbc.mapper.UserRowMapper;
import com.boroday.userstore.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao {

    private static final String GET_ALL_SQL = "select * from users";
    private static final String ADD_NEW_USER = "insert into users values (?,?,?,?,?)";
    private static final String REMOVE_USER = "delete from users where id=(?)";
    private static final String SELECT_USER_BY_ID = "select * from users where id=(?)";
    //private static final String UPDATE_USER = "update users set \"firstName\"='(?)', \"lastName\"='(?)', salary=(?), \"dateOfBirth\"='(?)' where id=(?)";

    private static final String UPDATE_USER = "update users set \"firstName\"=(?), \"lastName\"=(?) where id=(?)";

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

    public int addNewUser(HttpServletRequest request) {
        int countOfInsertedUsers = 0;
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER)
        ) {

            preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
            preparedStatement.setString(2, request.getParameter("firstName"));
            preparedStatement.setString(3, request.getParameter("lastName"));

            String salary = request.getParameter("salary");
            if (!salary.isEmpty()) {
                preparedStatement.setDouble(4, Double.parseDouble(request.getParameter("salary")));
            } else {

                preparedStatement.setNull(4, Types.DOUBLE);
            }

            String dateOfBirth = request.getParameter("dateOfBirth");
            if (!dateOfBirth.isEmpty()) {
                preparedStatement.setDate(5, Date.valueOf(request.getParameter("dateOfBirth")));
            } else {
                preparedStatement.setObject(5, null);
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
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        ) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
        } catch (
                SQLException ex)
        {
            ex.printStackTrace();
        }
        return user;
    }

    public int updateUser(HttpServletRequest request) {
        System.out.println("here in edit dao");
        int countOfUpdatedUsers = 0;
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)
        ) {

            System.out.println(request.getParameter("firstName"));
            preparedStatement.setString(1, request.getParameter("firstName"));
            preparedStatement.setString(2, request.getParameter("lastName"));

            /*String salary = request.getParameter("salary");
            if (!salary.isEmpty()) {
                preparedStatement.setDouble(3, Double.parseDouble(request.getParameter("salary")));
            } else {
                preparedStatement.setObject(3, null);
            }

            String dateOfBirth = request.getParameter("dateOfBirth");
            if (!dateOfBirth.isEmpty()) {
                preparedStatement.setDate(4, Date.valueOf(request.getParameter("dateOfBirth")));
            } else {
                preparedStatement.setObject(4, null);
            }*/
            preparedStatement.setInt(3, Integer.parseInt(request.getParameter("id")));

            countOfUpdatedUsers = preparedStatement.executeUpdate();
            System.out.println("here by count " + countOfUpdatedUsers);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return countOfUpdatedUsers;
    }


    private Connection getConnection() throws SQLException {
        String host = "jdbc:postgresql://localhost:5432/userstore";
        String username = "postgres";
        String password = "12345";

        return DriverManager.getConnection(host, username, password);
    }
}
