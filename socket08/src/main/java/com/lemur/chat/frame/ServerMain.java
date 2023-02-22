package com.lemur.chat.frame;

import com.lemur.chat.service.Server;

public class ServerMain {
    static {
        try {
            Class.forName("com.lemur.chat.utils.Mybatis");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
