package com.boroday.userstore.web.servlet;

import com.boroday.userstore.ServiceLocator;
import com.boroday.userstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveUserServlet extends HttpServlet {

    private static final String USERS_PAGE = "/users";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        String userId = request.getParameter("id");
        log.info("Request to remove user with id {}", userId);
        UserService userService = ServiceLocator.getService(UserService.class);
        userService.remove(userId);
        response.sendRedirect(USERS_PAGE);
    }
}
