package com.boroday.userstore.main;
import com.boroday.userstore.servlet.AddNewUserServlet;
import com.boroday.userstore.servlet.AllUsersServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        AddNewUserServlet addNewUserServlet = new AddNewUserServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(addNewUserServlet), "/users/add");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
