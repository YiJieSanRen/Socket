package com.lemur.chat.domain;

/**
 * 表示消息类型
 */
public interface MessageType {

    //表示登录成功
    String MESSAGE_LOGIN_SUCCESS = "1";

    //表示登录失败
    String MESSAGE_LOGIN_FAIL = "0";

    //普通信息包
    String MESSAGE_COMM_MES = "2";

    //请求在线用户列表
    String MESSAGE_GET_ONLINE_FRIEND = "3";

    //返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND = "4";

    //客户端请求退出
    String MESSAGE_CLIENT_EXIT = "5";

    //群发消息
    String MESSAGE_TO_ALL_MES = "6";

    //发送文件
    String MESSAGE_FILE_MES = "7";

}
