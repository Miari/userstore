package com.boroday.userstore.web.servlet;

import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RemoveUserServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        UserService userService = new UserService();

        String userId = request.getParameter("id");
        int removedUser = userService.remove(userId);
        pageVariables.put("users", userService.getAll());
        pageVariables.put("removedUser", removedUser);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("users.html", pageVariables);
        response.getWriter().write(page);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
