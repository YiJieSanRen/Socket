package com.lemur.chat.Provider;

import com.lemur.chat.domain.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class BaseUserProvider {

    public String checkUser(Map<String, Object> para) {
        User user = (User) para.get("user");
        return new SQL() {{
            SELECT("*").
                    FROM("user").
                    WHERE("user_name= '" + (user.getUserName()) + "'").
                    WHERE("password= '" + (user.getPasswd()) + "'");
        }}.toString();
    }

}
