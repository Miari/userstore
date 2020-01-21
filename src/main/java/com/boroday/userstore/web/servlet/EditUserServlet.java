package com.boroday.userstore.web.servlet;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import com.boroday.userstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {

    private UserService userService = new UserService();
    private static final String USERS_PAGE = "/users";

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
        try {
            User user = new User();
            user.setId(Integer.parseInt(request.getParameter("id")));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setSalary(Double.parseDouble(request.getParameter("salary")));
            Date date = Date.valueOf(request.getParameter("dateOfBirth"));
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }
}
