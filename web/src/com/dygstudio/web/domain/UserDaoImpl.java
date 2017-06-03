package com.dygstudio.web.domain;

import com.dygstudio.web.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by newsu on 2017/6/2.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseRepositoryImpl<User,String> implements UserDao {

}
