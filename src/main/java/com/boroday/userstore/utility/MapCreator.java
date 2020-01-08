package com.boroday.userstore.utility;

import com.boroday.userstore.database.ExecuteQuery;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapCreator {

    private static final String GET_ALL_USERS = "select * from users";

    public static Map<String, Object> createAllUsersMap() {
        Map<String, Object> pageVariables = new HashMap<>();
        try {
            ExecuteQuery executeQuery = new ExecuteQuery();
            pageVariables = executeQuery.getAllUsers(GET_ALL_USERS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pageVariables;
    }
}

