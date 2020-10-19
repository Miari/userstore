package com.boroday.userstore.web.controller;

import com.boroday.userstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    @ResponseBody
    public Map allUsers(HttpServletResponse response) throws IOException {
        log.info("Page for getting all users is requested");

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users", userService.getAll());
        return pageVariables;
    }
}
