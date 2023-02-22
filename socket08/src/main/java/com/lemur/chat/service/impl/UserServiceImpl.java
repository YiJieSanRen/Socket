package com.lemur.chat.service.impl;

import com.lemur.chat.domain.User;
import com.lemur.chat.mapper.UserMapper;
import com.lemur.chat.service.UserService;
import com.lemur.chat.utils.Mybatis;
import org.apache.ibatis.session.SqlSession;

import java.util.Objects;

/**
 * 连接数据库验证用户，注册用户等功能
 */
public class UserServiceImpl implements UserService {
    UserMapper userMapper;

    {
        SqlSession sqlSession = Mybatis.getSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    public boolean checkUser(User user) {
        User checkUser = userMapper.checkUser(user);
        if (Objects.nonNull(checkUser)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
