package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class AddNewUserServlet extends HttpServlet {

    private PageGenerator pageGenerator = PageGenerator.instance();
    private static final String USERS_PAGE = "/users";

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        String generatedPage = pageGenerator.getPage("adduser.html");
        response.getWriter().write(generatedPage);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        UserService userService = new UserService();
        try {
            User user = new User();
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setSalary(Double.parseDouble(request.getParameter("salary")));
            Date date = Date.valueOf(request.getParameter("dateOfBirth"));
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            userService.add(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }
}
