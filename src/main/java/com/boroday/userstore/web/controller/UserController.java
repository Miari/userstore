package com.boroday.userstore.web.controller;

import com.boroday.userstore.entity.User;
import com.boroday.userstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Controller
@Slf4j
public class UserController {
    private static final int BUFFER_SIZE = 8192;
    private static final String USERS_PAGE = "/users";
    private static final String SIGNIN_PAGE = "/signin";

    @Autowired
    private UserService userService;

    // all users
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String allUsers(Model model) {
        log.info("Page for getting all users is requested");
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    // add user
    @RequestMapping(path = "/users/add", method = RequestMethod.GET)
    public String addUser() {
        log.info("Page for adding new user is requested");
        return "adduser";
    }

    @RequestMapping(path = "/users/add", method = RequestMethod.POST)
    public void addUser(HttpServletResponse response,
                        @RequestParam("login") String login,
                        @RequestParam("password") String password,
                        @RequestParam("firstName") String firstName,
                        @RequestParam("lastName") String lastName,
                        @RequestParam("salary") String salary,
                        @RequestParam("dateOfBirth") String dateOfBirth) throws IOException {

        try {
            log.info("Request to add new user with name \"{}\" \"{}\"", firstName, lastName);

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setLogin(login);
            user.setPassword(password);
            user.setSalary(Double.parseDouble(salary));
            Date date = Date.valueOf(dateOfBirth);
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            userService.add(user);
        } catch (Exception e) {
            log.error("New user was nor added");
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }

    // edit user
    @RequestMapping(path = "/users/edit", method = RequestMethod.GET)
    public String editUserGet(@RequestParam("id") String userId, Model model) {
        log.info("Page for editing of user is requested");
        try {
            Long userIdLong = Long.parseLong(userId);
            User userForEdit = userService.getById(userIdLong);
            model.addAttribute("user", userForEdit);
            return "edituser";
        } catch (NumberFormatException e) {
            log.error("Format of id is incorrect");
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ID must be a number"
            );
        }
    }

    @RequestMapping(path = "/users/edit", method = RequestMethod.POST)
    public void editUserPost(HttpServletResponse response,
                             @RequestParam("id") String userId,
                             @RequestParam("login") String login,
                             @RequestParam("password") String password,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("salary") String salary,
                             @RequestParam("dateOfBirth") String dateOfBirth) throws IOException {
        try {
            log.info("Request to edit user with id {}", userId);
            User user = new User();
            user.setId(Long.parseLong(userId));
            user.setLogin(login);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setSalary(Double.parseDouble(salary));
            Date date = Date.valueOf(dateOfBirth);
            Timestamp timestamp = new Timestamp(date.getTime());
            user.setDateOfBirth(timestamp.toLocalDateTime().toLocalDate());
            userService.update(user);
        } catch (Exception e) {
            log.error("User was nor edited");
            e.printStackTrace();
        }
        response.sendRedirect(USERS_PAGE);
    }

    // remove user
    @RequestMapping(path = "/users/remove", method = RequestMethod.POST)
    public void removeUser(HttpServletResponse response,
                           @RequestParam("id") String userId) throws IOException {
        try {
            Long userIdLong = Long.parseLong(userId);
            log.info("Request to remove user with id {}", userId);
            userService.remove(userIdLong);
            response.sendRedirect(USERS_PAGE);
        } catch (NumberFormatException e) {
            log.error("Format of id is incorrect");
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ID must be a number"
            );
        }
    }

    // search user
    @RequestMapping(path = "/users/search", method = RequestMethod.GET)
    public String searchUser(@RequestParam("searchText") String searchText, Model model) {
        log.info("Request to search users by text: {}", searchText);

        List<User> foundUsers = userService.search(searchText);
        model.addAttribute("users", foundUsers);
        model.addAttribute("foundUsers", foundUsers.size());

        return "users";
    }

    // sign in
    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public String signIn() {
        log.info("Signin page is requested");
        return "signin";
    }

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public String signIn(HttpServletResponse response,
                         @RequestParam("login") String login,
                         @RequestParam("password") String password, Model model) throws IOException {
        User userToLogin = userService.getByLogin(login, password);

        if (userToLogin != null) {
            log.debug("User with login {} is authorized", login);
            response.sendRedirect(USERS_PAGE);
        } else {
            log.debug("User with login {} is not authorized", login);
            model.addAttribute("message", "User is not registered or password is incorrect");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "signin";
        }
        return "";
    }

    // sign out
    @RequestMapping(path = "/signout", method = RequestMethod.GET)
    public void signOut(HttpServletResponse response) throws IOException {
        log.info("Sign out is requested");
        response.sendRedirect(SIGNIN_PAGE);
    }

    // assets
    @RequestMapping(path = "/assets/css/style.css", method = RequestMethod.GET)
    public void assetsStyle(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String requestURI = request.getRequestURI().substring(1);
        getAssets(response, requestURI);
    }

    @RequestMapping(path = "/assets/css/reset.css", method = RequestMethod.GET)
    public void assetsReset(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String requestURI = request.getRequestURI().substring(1);
        getAssets(response, requestURI);
    }

    public void getAssets(HttpServletResponse response, String requestURI) {
        log.info("Following resources are requested: {}", requestURI);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(requestURI)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int numberBytesRead;
            while ((numberBytesRead = inputStream.read(buffer)) > 0) {
                response.getOutputStream().write(buffer, 0, numberBytesRead);
            }
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (NullPointerException e) {
            log.error("Resources URI is not found");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("Not possible to get resource by the request: " + requestURI);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("Not possible to get resource by the request: " + requestURI, e);
        }
    }
}
