package com.dygstudio.web.domain;

import com.dygstudio.web.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by newsu on 2017/6/2.
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl extends BaseRepositoryImpl<User,String> implements UserDao {

}
