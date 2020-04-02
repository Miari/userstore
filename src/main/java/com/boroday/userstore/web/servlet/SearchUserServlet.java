package com.boroday.userstore.web.servlet;

import com.boroday.userstore.ServiceLocator;
import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.service.impl.DefaultUserService;
import com.boroday.userstore.web.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchUserServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {


        Map<String, Object> pageVariables = new HashMap<>();
        UserService userService = ServiceLocator.getService(UserService.class);

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
