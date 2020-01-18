package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = new HashMap<>();

        String userId = request.getParameter("id");
        User userForEdit = userService.getById(userId);
        pageVariables.put("user", userForEdit);


        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("edituser.html", pageVariables);
        response.getWriter().write(page);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        int resultOfUpdate = 0;
        try {
            User user = new User();
            user.setId(Integer.parseInt(request.getParameter("id")));
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
            resultOfUpdate = userService.update(user);
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

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("users.html", pageVariables);
        response.getWriter().write(page);
    }
}
