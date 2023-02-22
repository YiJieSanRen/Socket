package com.lemur.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //id
    private String id;

    //用户id
    private String userId;

    //用户名称
    private String userName;

    //用户密码
    private String passwd;

}
