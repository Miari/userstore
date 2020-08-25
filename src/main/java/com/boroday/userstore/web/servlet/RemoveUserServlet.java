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

@Slf4j
public class RemoveUserServlet extends HttpServlet {

    private static final String USERS_PAGE = "/users";
    private UserService userService;

    /*public void setUserService(DefaultUserService userService) {
        this.userService = userService;
    } */

    public RemoveUserServlet(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        String userId = request.getParameter("id");
        try {
            Long userIdLong = Long.parseLong(userId);
            log.info("Request to remove user with id {}", userId);
            userService.remove(userIdLong);
            response.sendRedirect(USERS_PAGE);
        } catch (NumberFormatException e) {
            log.error("Format of id is incorrect");
            e.printStackTrace();
            response.getWriter().println("Status code (400) ID must be a number");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }



    }
}
