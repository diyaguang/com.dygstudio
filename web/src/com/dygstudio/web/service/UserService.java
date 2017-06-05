package com.dygstudio.web.service;

import com.dygstudio.web.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by newsu on 2017/6/2.
 */
@Service("userService")
@Transactional
public interface UserService {
    public List<User> getAllUser();
    public void saveUser(User user);
    public int login(User user);
    public User getUser(String userId);
}
