package com.boroday.userstore.servlet;

import com.boroday.userstore.database.ExecuteQuery;
import com.boroday.userstore.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddNewUserServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        String s = new String(Files.readAllBytes(Paths.get("templates" + File.separator + "adduser.html")));
        response.getWriter().write(s);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        int resultOfInsert = 0;
        try {
            resultOfInsert = ExecuteQuery.insertUser(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");

        if (resultOfInsert == 0) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        System.out.println(resultOfInsert);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", resultOfInsert == 1 ? 1 : 0);

        response.getWriter().println(PageGenerator.instance().getPage("addeduser.html", pageVariables));
    }
}
