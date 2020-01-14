package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchUserServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        UserService userService = new UserService();

        String searchText =  request.getParameter("searchText");
        List<User> foundUsers = userService.searchUser(searchText);
        pageVariables.put("users", foundUsers);
        pageVariables.put("foundUsers", foundUsers.size());
        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
