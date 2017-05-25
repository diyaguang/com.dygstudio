package com.dygstudio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by newsu on 2017/5/25.
 */
@Controller
public class HomeController {

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String Home(){
        return "index";
    }
}
