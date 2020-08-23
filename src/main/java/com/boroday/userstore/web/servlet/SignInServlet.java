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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class SignInServlet extends HttpServlet {

    private UserService userService;
    private static final String USERS_PAGE = "/users";

    public void setUserService(DefaultUserService userService) {
        this.userService = userService;
    }

    /*
    public SignInServlet(UserService userService) {
        this.userService = userService;
    }
     */

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Signin page is requested");
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("signin.html");
        response.getWriter().write(page);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");

        User userToLogin = userService.getByLogin(userLogin, userPassword);
        //Optional<User> userToLogin1 = Optional.ofNullable(userService.getByLogin(userLogin, userPassword));

        if (userToLogin != null) {
            log.debug("User with login {} is authorized", userLogin);
            response.sendRedirect(USERS_PAGE);
        } else {
            log.debug("User with login {} is not authorized", userLogin);
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("message", "User is not registered or password is incorrect");
            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("signin.html", pageVariables);
            response.getWriter().write(page);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
