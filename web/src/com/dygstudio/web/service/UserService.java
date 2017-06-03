package com.dygstudio.web.service;

import com.dygstudio.web.entity.User;

import java.util.List;

/**
 * Created by newsu on 2017/6/2.
 */
public interface UserService {
    public List<User> getAllUser();
    public void saveUser(User user);
    public int login(User user);
}
