package com.boroday.userstore.web.servlet;

import com.boroday.userstore.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveUserServlet extends HttpServlet {

    private static final String USERS_PAGE = "/users";

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        UserService userService = new UserService();
        String userId = request.getParameter("id");
        userService.remove(userId);
        response.sendRedirect(USERS_PAGE);
    }
}
