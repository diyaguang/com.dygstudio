package com.dygstudio.web.controller;

import com.dygstudio.web.domain.UserDao;
import com.dygstudio.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by diyaguang on 2017/6/12.
 */
@Controller
@RequestMapping("/api")
public class ApiController {
    private static final String MAX_LONG_AS_STRING="9223372036854775807";
    private UserDao userRepository;

    @Autowired
    public ApiController(UserDao userDao){
        this.userRepository = userDao;
    }

    @RequestMapping(method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<User> getUsers(@RequestParam(value = "max",defaultValue = MAX_LONG_AS_STRING) long max,
                        @RequestParam(value = "count",defaultValue = "20") int count){
        return userRepository.getAll();
    }
}
