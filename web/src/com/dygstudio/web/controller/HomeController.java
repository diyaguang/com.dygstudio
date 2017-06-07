package com.dygstudio.web.controller;

import com.dygstudio.web.entity.User;
import com.dygstudio.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by newsu on 2017/5/25.
 */
@Controller
public class HomeController {
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String Home(Model model){
        model.addAttribute("name","dygstudio");
        return "index";
    }
}
