package com.lemur.chat.service;

import java.util.HashMap;

/**
 * 管理客户端连接到服务器端的线程类
 */
public class ManageClientConnectServerThread {
    //把多个线程放入到一个HashMap集合，key 就是用户id，value 就是线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //将某个线程加入到集合
    public static void addClientConnectServerThread(String username, ClientConnectServerThread clientConnectServerThread) {
        hm.put(username, clientConnectServerThread);
    }

    //通过用户名获取到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String username) {
        return hm.get(username);
    }
}
