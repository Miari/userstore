package com.boroday.userstore.servlet;

import com.boroday.userstore.templater.PageGenerator;
import com.boroday.userstore.utility.MapCreator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AllUsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = MapCreator.createAllUsersMap();

        response.getWriter().println(PageGenerator.instance().getPage("users.html", pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }
}


