package com.boroday.userstore.web.servlet;

import com.boroday.userstore.ServiceLocator;
import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.impl.DefaultUserService;
import com.boroday.userstore.web.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {

    private DefaultUserService userService = ServiceLocator.getService(DefaultUserService.class);
    private static final String USERS_PAGE = "/users";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        log.info("Page for editing of user is requested");
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
            String userId = request.getParameter("id");
            log.info("Request to edit user with id {}", userId);
            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setSalary(Double.parseDouble(request.getParameter("salary")));
            Date date = Date.valueOf(request.getParameter("dateOfBirth"));
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            userService.update(user);
        } catch (Exception e) {
            log.error("User was nor edited");
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }
}
