package com.lemur.chat.service.impl;

import com.lemur.chat.domain.Message;
import com.lemur.chat.domain.MessageType;
import com.lemur.chat.builder.SendMessageBuilder;
import com.lemur.chat.service.BaseService;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 消息发送
 */
public class MessageClientService extends BaseService {

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

}
