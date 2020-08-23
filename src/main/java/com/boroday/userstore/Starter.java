package com.boroday.userstore;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.Set;

import com.boroday.ioc.context.ApplicationContext;
import com.boroday.ioc.context.ClassPathApplicationContext;
import com.boroday.userstore.dao.DataSourceFactory;
import com.boroday.userstore.dao.UserDao;
import com.boroday.userstore.dao.jdbc.JdbcUserDao;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.service.impl.DefaultUserService;
import com.boroday.userstore.util.PropertiesReader;
import com.boroday.userstore.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
public class Starter {
    public static void main(String[] args) throws Exception {

        ApplicationContext applicationContext = new ClassPathApplicationContext(new String[]{"dao-context.xml", "service-context.xml", "servlet-context.xml"});

        //printSystemVariables();
        //printEnvironmentVariables();

        PropertiesReader propertiesReader = new PropertiesReader("application.properties");
        Properties properties = propertiesReader.getProperties();
        DataSource dataSource = DataSourceFactory.getDataSource(properties);

        UserDao userDao = new JdbcUserDao(dataSource);
        UserService userService = new DefaultUserService(userDao);

        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        SignInServlet signInServlet = new SignInServlet(userService);
        SignOutServlet signOutServlet = new SignOutServlet(userService);
        AddNewUserServlet addNewUserServlet = new AddNewUserServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        SearchUserServlet searchUserServlet = new SearchUserServlet(userService);
        RemoveUserServlet removeUserServlet = new RemoveUserServlet(userService);
        DefaultServlet defaultServlet = new DefaultServlet();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(signInServlet), "/signin");
        context.addServlet(new ServletHolder(signOutServlet), "/signout");
        context.addServlet(new ServletHolder(addNewUserServlet), "/users/add");
        context.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        context.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        context.addServlet(new ServletHolder(searchUserServlet), "/users/search");

        ServletHolder servletHolder = new ServletHolder("default", defaultServlet);
        servletHolder.setInitParameter("resourceBase", "./src/main/resources/");
        context.addServlet(servletHolder, "/");

        //changed to DefaultServlet
        //context.addServlet(new ServletHolder(new GetStaticResourcesServlet()), "/");

        //PropertiesReader propertiesReader = ServiceLocator.getService(PropertiesReader.class);
        int port = propertiesReader.getPropertyInt("server.port");

        Server server = new Server(port);
        server.setHandler(context);

        server.start();
    }

    // system variables
    // environment variables
    private static void printSystemVariables() {
        log.info("SYSTEM VARIABLES START");
        Properties properties = System.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            log.info(entry.getKey() + " = " + entry.getValue());
        }

        log.info("SYSTEM VARIABLES STOP");
    }

    private static void printEnvironmentVariables() {
        log.info("ENVIRONMENT VARIABLES START");
        Map<String, String> properties = System.getenv();
        Set<Map.Entry<String, String>> entries = properties.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            log.info(entry.getKey() + " = " + entry.getValue());
        }

        log.info("ENVIRONMENT VARIABLES STOP");
    }
}

