package com.boroday.userstore;

import com.boroday.userstore.util.PropertiesReader;
import com.boroday.userstore.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        AddNewUserServlet addNewUserServlet = new AddNewUserServlet();
        EditUserServlet editUserServlet = new EditUserServlet();
        SearchUserServlet searchUserServlet = new SearchUserServlet();
        RemoveUserServlet removeUserServlet = new RemoveUserServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(addNewUserServlet), "/users/add");
        context.addServlet(new ServletHolder(removeUserServlet), "/users/remove");
        context.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        context.addServlet(new ServletHolder(searchUserServlet), "/users/search");

        PropertiesReader propertiesReader = ServiceLocator.getService(PropertiesReader.class);
        int port = propertiesReader.getPropertyInt("server.port");

        Server server = new Server(port);
        server.setHandler(context);

        server.start();
    }
}
