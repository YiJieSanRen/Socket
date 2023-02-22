package com.lemur.chat.service;

import com.lemur.chat.builder.ReceiverMessageBuilder;
import com.lemur.chat.domain.Message;
import com.lemur.chat.domain.MessageType;
import lombok.Getter;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * 服务器与客户端通信线程
 */
public class ServerConnectClientThread extends Thread {

    Log log = LogFactory.getLog(ServerConnectClientThread.class);

    //该线程需要持有Socket
    @Getter
    private Socket socket;

    @Getter
    private String userName;

    //构造器可以接收一个Socket对象
    public ServerConnectClientThread(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
    }

    @Override
    public void run() {
        //因为Thread需要在后台和服务器通信，因此我们while循环
        log.debug("用户" + userName + "登录");
        while (Boolean.TRUE) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //根据message的类型，做相应的业务处理
                if (MessageType.MESSAGE_GET_ONLINE_FRIEND.equals(message.getMesType())) {
                    //客户端在在线用户列表
                    log.debug("用户" + message.getSender() + "查看在线用户列表");
                    String onlineUser = ManageServerConnectClientThread.getOnlineUser();
                    sendMess(socket, new ReceiverMessageBuilder(MessageType.MESSAGE_RET_ONLINE_FRIEND).setContent(onlineUser).setReceiver(message.getSender()).build());
                } else if (MessageType.MESSAGE_CLIENT_EXIT.equals(message.getMesType())) {
                    //客户端退出
                    log.debug("用户" + userName + "退出系统！");
                    sendMess(socket, new ReceiverMessageBuilder(MessageType.MESSAGE_CLIENT_EXIT).setReceiver(message.getSender()).build());
                    ManageServerConnectClientThread.removeServerConnectClientThread(userName);
                    //关闭连接
                    socket.close();
                    //退出循环
                    break;
                } else if (MessageType.MESSAGE_COMM_MES.equals(message.getMesType())) {
                    //私聊
                    log.debug("用户" + message.getSender() + "给用户" + message.getReceiver() + "发送了一条消息");
                    ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getReceiver());
                    sendMess(serverConnectClientThread.getSocket(), message);
                } else if (MessageType.MESSAGE_TO_ALL_MES.equals(message.getMesType())) {
                    //群聊
                    log.debug("用户" + message.getSender() +"发送了一条群聊消息");
                    Map<String, ServerConnectClientThread> hm = ManageServerConnectClientThread.getHm();
                    for (Map.Entry<String, ServerConnectClientThread> entry : hm.entrySet()) {
                        String userName = entry.getKey();
                        ServerConnectClientThread serverConnectClientThread = entry.getValue();
                        if (!userName.equals(message.getSender())) {
                            sendMess(serverConnectClientThread.getSocket(), message);
                        }
                    }
                } else if (MessageType.MESSAGE_FILE_MES.equals(message.getMesType())) {
                    //发送文件
                    log.debug("用户" + message.getSender() + "给用户" + message.getReceiver() + "发送了一个文件");
                    ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getReceiver());
                    sendMess(serverConnectClientThread.getSocket(), message);
                } else {
                    log.debug("消息未识别！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
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
