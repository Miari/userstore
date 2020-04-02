package com.boroday.userstore.web.servlet;

import com.boroday.userstore.ServiceLocator;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.service.impl.DefaultUserService;
import com.boroday.userstore.web.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AllUsersServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Page for getting all users is requested");
        Map<String, Object> pageVariables = new HashMap<>();
        UserService userService = ServiceLocator.getService(UserService.class);

        //pageVariables.put("users", createMockList());
        pageVariables.put("users", userService.getAll());

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("users.html", pageVariables);
        response.getWriter().write(page);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }



    /*private List<User> createMockList() {
        List<User> usersList = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setFirstName("Kim");
        user.setLastName("Wolf");
        user.setSalary(1520.00);
        user.setDateOfBirth(LocalDate.of(2000, Month.DECEMBER, 11));

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Anna");
        user2.setLastName("Fox");
        user2.setSalary(1330.00);
        user2.setDateOfBirth(LocalDate.of(1981, Month.FEBRUARY, 5));

        usersList.add(user);
        usersList.add(user2);

        return usersList;
    }*/
}


