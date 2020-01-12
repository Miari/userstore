package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class AllUsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        UserService userService = new UserService();

        //pageVariables.put("users", createMockList());
        pageVariables.put("users", userService.getAll());

        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        UserService userService = new UserService();


        Enumeration<String> en = request.getParameterNames();
        String parameterName = en.nextElement();
        String buttonValue = request.getParameter(parameterName);

        if (buttonValue.equals("Delete")) {
            int removedUser = userService.removeUser(parameterName);
            pageVariables.put("users", userService.getAll());
            pageVariables.put("removedUser", removedUser);
        } else {
            pageVariables.put("users", userService.getAll());
        }

        /*User userForEdit = userService.getUserById(parameterName);
                pageVariables.put("user", userForEdit);
                response.getWriter().println(PageGenerator.instance().getPage("edituser.html", pageVariables));*/

        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    private List<User> createMockList() {
        List<User> usersList = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setFirstName("Kim");
        user.setLastName("Wolf");
        user.setSalary(1520);
        user.setDateOfBirth(LocalDate.of(2000, Month.DECEMBER, 11));

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Anna");
        user2.setLastName("Fox");
        user2.setSalary(1330);
        user2.setDateOfBirth(LocalDate.of(1981, Month.FEBRUARY, 5));

        usersList.add(user);
        usersList.add(user2);

        return usersList;
    }
}


