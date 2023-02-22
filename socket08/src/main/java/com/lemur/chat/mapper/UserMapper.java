package com.lemur.chat.mapper;

import com.lemur.chat.Provider.BaseUserProvider;
import com.lemur.chat.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface UserMapper {

    @SelectProvider(type= BaseUserProvider.class,method="checkUser")
    User checkUser(@Param("user")User user);
}
