package com.lemur.chat.service;

import com.lemur.chat.domain.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class BaseService {

    protected String sender;

    protected void sendMess(Message message) {
        try {
            //从管理线程的集合中，通过user得到这个线程
            ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(sender);
            //通过线程得到关联的socket
            Socket socket = clientConnectServerThread.getSocket();
            //发送给服务器
            //得到当前线程的Socket 对应的 ObjectOutputStream 对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
