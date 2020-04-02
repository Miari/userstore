package com.boroday.userstore;

import com.boroday.userstore.dao.ConnectionFactory;
import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.service.impl.DefaultUserService;
import com.boroday.userstore.util.PropertiesReader;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();
    private static final String PROPERTIES_FILE = "application.properties";

    static {
        PropertiesReader propertiesReader = new PropertiesReader(PROPERTIES_FILE);
        register(PropertiesReader.class, propertiesReader);

        Properties properties = propertiesReader.getProperties();
        DataSource dataSource = ConnectionFactory.getDataSource(properties);

        UserDao userDao = new JdbcUserDao(dataSource);
        register(UserDao.class, userDao);

        UserService userService = new DefaultUserService(userDao);
        register(UserService.class, userService);
    }

    public static void register(Class<?> serviceClass, Object service) {
        SERVICES.put(serviceClass, service);
    }

    public static <T> T getService(Class<T> serviceClass) {
        return serviceClass.cast(SERVICES.get(serviceClass));
    }
}
