package com.dygstudio.web.controller;

import com.dygstudio.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by newsu on 2017/6/14.
 */
@Controller
@RequestMapping(value = "/client")
public class TestAPIClient {

    @Autowired
    protected RestTemplate restTemplate;

    //模拟 GET 操作，该请求有两中方法 getForObject(),getForEntity() 后者可以返回对象以及响应相关的额外信息。
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String getAllUser(){
        List<User> users = restTemplate.getForObject("http://localhost:8080/api/users",List.class);
        return "index";
    }

}
