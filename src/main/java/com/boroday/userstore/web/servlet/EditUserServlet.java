package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        UserService userService = new UserService();

        Enumeration<String> listOfParameters = request.getParameterNames();
        String userId = listOfParameters.nextElement();
        User userForEdit = userService.getUserById(userId);
        pageVariables.put("user", userForEdit);
        response.getWriter().println(PageGenerator.instance().getPage("edituser.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        UserService userService = new UserService();
        int resultOfUpdate = 0;
        try {
            resultOfUpdate = userService.updateUser(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");

        if (resultOfUpdate == 0) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", userService.getAll());
        pageVariables.put("editedUser", resultOfUpdate == 1 ? 1 : 0);

        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));
    }
}
