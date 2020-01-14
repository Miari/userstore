package com.boroday.userstore;
import com.boroday.userstore.web.servlet.AddNewUserServlet;
import com.boroday.userstore.web.servlet.AllUsersServlet;
import com.boroday.userstore.web.servlet.EditUserServlet;
import com.boroday.userstore.web.servlet.SearchUserServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {

        AllUsersServlet allUsersServlet = new AllUsersServlet();
        AddNewUserServlet addNewUserServlet = new AddNewUserServlet();
        EditUserServlet editUserServlet = new EditUserServlet();
        SearchUserServlet searchUserServlet = new SearchUserServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(addNewUserServlet), "/users/add");
        context.addServlet(new ServletHolder(allUsersServlet), "/users/remove");
        context.addServlet(new ServletHolder(editUserServlet), "/users/edit");
        context.addServlet(new ServletHolder(searchUserServlet), "/users/search");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
