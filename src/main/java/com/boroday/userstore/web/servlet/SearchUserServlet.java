package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SearchUserServlet extends HttpServlet {

    private UserService userService;

    public SearchUserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {


        Map<String, Object> pageVariables = new HashMap<>();

        String searchText = request.getParameter("searchText");
        log.info("Request to search users by text: {}", searchText);

        List<User> foundUsers = userService.search(searchText);
        pageVariables.put("users", foundUsers);
        pageVariables.put("foundUsers", foundUsers.size());

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("users.html", pageVariables);
        response.getWriter().write(page);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
