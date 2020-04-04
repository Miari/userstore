package com.boroday.userstore.web.servlet;

import com.boroday.userstore.ServiceLocator;
import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class AddNewUserServlet extends HttpServlet {

    private PageGenerator pageGenerator = PageGenerator.instance();
    private static final String USERS_PAGE = "/users";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Page for adding new user is requested");

        String generatedPage = pageGenerator.getPage("adduser.html");
        response.getWriter().write(generatedPage);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        try {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            log.info("Request to add new user with name \"{}\" \"{}\"", firstName, lastName);

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setSalary(Double.parseDouble(request.getParameter("salary")));
            Date date = Date.valueOf(request.getParameter("dateOfBirth"));
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            UserService userService = ServiceLocator.getService(UserService.class);
            userService.add(user);
        } catch (Exception e) {
            log.error("New user was nor added");
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }
}
