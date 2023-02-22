package com.lemur.chat.service;

import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 管理客户端连接到服务器端的线程类
 */
public class ManageServerConnectClientThread {
    //把多个线程放入到一个HashMap集合，key 就是用户id，value 就是线程
    @Getter
    private static Map<String, ServerConnectClientThread> hm = new HashMap<>();

    //将某个线程加入到集合
    public static void addClientConnectServerThread(String username, ServerConnectClientThread serverConnectClientThread) {
        hm.put(username, serverConnectClientThread);
    }

    //通过用户名获取到对应线程
    public static ServerConnectClientThread getServerConnectClientThread(String username) {
        return hm.get(username);
    }

    //移除某个线程对象
    public static void removeServerConnectClientThread(String username) {
        hm.remove(username);
    }

    //返回在线用户列表
    public static String getOnlineUser() {
        Iterator<String> iterator = hm.keySet().iterator();
        StringJoiner onlineUserList = new StringJoiner(" ");
        while (iterator.hasNext()) {
            onlineUserList.add(iterator.next());
        }
        return onlineUserList.toString();
    }
}
