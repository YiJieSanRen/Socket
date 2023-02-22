package com.lemur.chat.service;

import com.lemur.chat.domain.Message;
import com.lemur.chat.domain.MessageType;
import com.lemur.chat.domain.SendMessageBuilder;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 消息发送
 */
public class MessageClientService {

    private String sender;

    /**
     * 群聊
     * @param mes
     * @param sender
     */
    public void sendMessageToAll(String mes, String sender) {
        this.sender = sender;
        //构建用户
        Message message = new SendMessageBuilder(MessageType.MESSAGE_TO_ALL_MES).setSender(sender).setContent(mes).build();
        System.out.println(sender + "对大家说：" + mes);
        //发送给服务端
        sendMess(message);
    }

    /**
     * 私聊
     * @param content
     * @param sender
     * @param receiver
     */
    public void sendMessageToOne(String content, String sender, String receiver) {
        this.sender = sender;
        //构建用户
        Message message = new SendMessageBuilder(MessageType.MESSAGE_COMM_MES).setSender(sender).setReceiver(receiver).setContent(content).build();
        System.out.println(sender + "对" + receiver + "说：" + content);
        //发送给服务端
        sendMess(message);
    }

    private void sendMess(Message message) {
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
