package com.boroday.userstore.web.servlet;

import com.boroday.userstore.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SignOutServlet extends HttpServlet {

    private UserService userService;
    private static final String SIGNIN_PAGE = "/signin";

    public SignOutServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Sign out is requested");
        response.sendRedirect(SIGNIN_PAGE);
    }
}
