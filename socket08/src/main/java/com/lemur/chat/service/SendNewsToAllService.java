package com.lemur.chat.service;

import com.lemur.chat.builder.ReceiverMessageBuilder;
import com.lemur.chat.domain.Message;
import com.lemur.chat.domain.MessageType;
import com.lemur.chat.utils.Utility;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class SendNewsToAllService implements Runnable {

    private final String EXIT = "exit";
    Log log = LogFactory.getLog(SendNewsToAllService.class);

    @Override
    public void run() {
        while (Boolean.TRUE) {
            System.out.println("请输入服务器要推送的新闻/消息[‘exit’关闭推送服务]");
            String news = Utility.readString(100);
            if (EXIT.equals(news)) {
                break;
            }
            Message message = new ReceiverMessageBuilder(MessageType.MESSAGE_TO_ALL_MES).setContent(news).setSender("管理员").build();
            log.debug("管理员下发了一条公告消息:" + news);
            Map<String, ServerConnectClientThread> hm = ManageServerConnectClientThread.getHm();
            for (Map.Entry<String, ServerConnectClientThread> entry : hm.entrySet()) {
                String userName = entry.getKey();
                ServerConnectClientThread serverConnectClientThread = entry.getValue();
                if (!userName.equals(message.getSender())) {
                    try {
                        sendMess(serverConnectClientThread.getSocket(), message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 返回给客户端
     */
    private void sendMess(Socket socket, Message message) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
    }
}
