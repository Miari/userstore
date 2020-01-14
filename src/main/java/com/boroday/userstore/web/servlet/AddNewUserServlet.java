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
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNewUserServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        String generatedPage = new String(Files.readAllBytes(Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "templates" + File.separator + "adduser.html")));
        response.getWriter().write(generatedPage);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        UserService userService = new UserService();
        int resultOfInsert = 0;
        try {
            User user = new User();
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));

            String salary = request.getParameter("salary");
            if (!salary.isEmpty()) {
                user.setSalary(Double.parseDouble(request.getParameter("salary")));
            }

            String dateOfBirth = request.getParameter("dateOfBirth");
            if (!dateOfBirth.isEmpty()) {
                user.setDateOfBirth((Date.valueOf(request.getParameter("dateOfBirth"))).toLocalDate());
            }

            resultOfInsert = userService.addNewUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");

        if (resultOfInsert == 0) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", resultOfInsert == 1 ? 1 : 0);

        response.getWriter().println(PageGenerator.instance().getPage("addeduser.html", pageVariables));
    }
}
