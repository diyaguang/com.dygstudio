package com.dygstudio.web.controller;

import com.dygstudio.web.domain.UserDao;
import com.dygstudio.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.net.URI;
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

    /**
     * 配置返回所有用户信息
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(@RequestParam(value = "max",defaultValue = MAX_LONG_AS_STRING) long max,
                        @RequestParam(value = "count",defaultValue = "20") int count){
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        String testParam = servletContext.getInitParameter("baseUrl");
        //增加额外的头信息 ------------------------------------------------------
        HttpHeaders headers = new HttpHeaders();
        headers.set("testString",testParam);
        //-------------------------------------------------------------------------
        return new ResponseEntity<List<User>>(userRepository.getAll(),headers, HttpStatus.OK);
    }

    /**
     * 接收客户端发送过来的 POST 请求，用来处理客户端传来的 JSON 格式数据。并将其转换为实体对象操作。
     * */
    @RequestMapping(value = "/users",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public  ResponseEntity<?> saveUser(@RequestBody User user){
        userRepository.save(user);
        //增加额外的头信息 ------------------------------------------------------
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = URI.create("http://loaclhost:8080/api/user/"+user.getId());
        headers.setLocation(locationUri);
        //-------------------------------------------------------------------------
        return new ResponseEntity<User>(user,headers,HttpStatus.CREATED);
    }

    /**
     * 获取指定 id 的实体对象，如果没有查询到，则页面状态码返回 404，即 这个可以自定义返回的状态码。
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<User> getUser(@PathVariable String id){
        User user = userRepository.get(id);
        HttpStatus status = user!=null?HttpStatus.OK:HttpStatus.NOT_FOUND;
        return new ResponseEntity<User>(user,status);
    }
}
