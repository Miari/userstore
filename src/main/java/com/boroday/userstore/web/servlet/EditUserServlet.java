package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.service.impl.DefaultUserService;
import com.boroday.userstore.web.templater.PageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EditUserServlet extends HttpServlet {

    private UserService userService;
    private static final String USERS_PAGE = "/users";

    /*public void setUserService(DefaultUserService userService) {
        this.userService = userService;
    } */

    public EditUserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Page for editing of user is requested");
        Map<String, Object> pageVariables = new HashMap<>();

        String userId = request.getParameter("id");

        try {
            Long userIdLong = Long.parseLong(userId);
            User userForEdit = userService.getById(userIdLong);
            pageVariables.put("user", userForEdit);

            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("edituser.html", pageVariables);
            response.getWriter().write(page);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            log.error("Format of id is incorrect");
            e.printStackTrace();
            response.getWriter().println("Status code (400) ID must be a number");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        try {
            String userId = request.getParameter("id");
            log.info("Request to edit user with id {}", userId);
            User user = new User();
            user.setId(Long.parseLong(userId));
            user.setLogin(request.getParameter("login"));
            user.setPassword(request.getParameter("password"));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setSalary(Double.parseDouble(request.getParameter("salary")));
            Date date = Date.valueOf(request.getParameter("dateOfBirth"));
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            userService.update(user);
        } catch (Exception e) {
            log.error("User was nor edited");
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }
}
