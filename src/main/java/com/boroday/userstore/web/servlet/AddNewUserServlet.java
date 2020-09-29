package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.service.impl.DefaultUserService;
import com.boroday.userstore.web.templater.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
public class AddNewUserServlet extends HttpServlet {

    private static final String USERS_PAGE = "/users";
    private UserService userService;

    /*public void setUserService(DefaultUserService userService) {
        this.userService = userService;
    }*/

    public AddNewUserServlet(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Page for adding new user is requested");

        PageGenerator pageGenerator = PageGenerator.instance();
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
            user.setLogin(request.getParameter("login"));
            user.setPassword(request.getParameter("password"));
            user.setSalary(Double.parseDouble(request.getParameter("salary")));
            Date date = Date.valueOf(request.getParameter("dateOfBirth"));
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            userService.add(user);
        } catch (Exception e) {
            log.error("New user was nor added");
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }
}
