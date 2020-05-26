package com.boroday.userstore;

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

public class Starter {
    public static void main(String[] args) throws Exception {

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

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(signInServlet), "/signin");
        context.addServlet(new ServletHolder(signOutServlet), "/signout");
        context.addServlet(new ServletHolder(addNewUserServlet), "/users/add");
        context.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        context.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        context.addServlet(new ServletHolder(searchUserServlet), "/users/search");

        DefaultServlet defaultServlet = new DefaultServlet();
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
}
