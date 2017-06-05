package com.dygstudio.web.controller;

import com.dygstudio.web.entity.User;
import com.dygstudio.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by diyaguang on 2017/6/5.
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户页面默认打开方法
     * @param model 该参数用来填充获取的数据，model的结构为 Map，这样会传递给视图，来渲染到客户端。
     *              该值也可以修改为直接使用 Map 作为参数。使用 map 的 put()方法压入数据
     * @param pageIndex   参数格式：/user?pageIndex=1&pageSize=30，默认可以不写
     * @param pageSize    参数格式：/user?pageIndex=1&pageSize=30，默认可以不写
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public String Home(Model model,
                       @RequestParam(value = "pageIndex",defaultValue ="0") int pageIndex,
                       @RequestParam(value = "pageSize",defaultValue = "10") int pageSize )
    {

        List<User> result = userService.getAllUser();
        model.addAttribute("userList",result);
        return "user/userList";
    }
    @RequestMapping(value = "/register" ,method=RequestMethod.GET)
    public String Register(){
        return "user/userInfo";
    }
    @RequestMapping(value = "/register/{userId}",method = RequestMethod.GET)
    public String Register(@PathVariable(value = "userId") String userId,
                           Model model)
    {
        User user = userService.getUser(userId);
        if(user != null)
            model.addAttribute("user",user);
        return "user/userInfo";
    }

}
