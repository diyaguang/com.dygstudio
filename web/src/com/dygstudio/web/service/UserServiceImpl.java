package com.dygstudio.web.service;

import com.dygstudio.web.domain.UserDao;
import com.dygstudio.web.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by newsu on 2017/6/2.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAllUser()
    {
        return userDao.getAll();
    }
    public void saveUser(User user){
        userDao.save(user);
    }
    public int login(User user){
        //这里对传进来的用户与数据库中查询到的用户的数据进行比对，判断是否可以登录
        return 0;
    }
}
