package com.lemur.chat.service;

import com.lemur.chat.domain.Message;
import com.lemur.chat.domain.MessageType;
import lombok.Getter;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 读取服务器响应消息线程
 */
public class ClientConnectServerThread extends Thread {

    Log log = LogFactory.getLog(ClientConnectServerThread.class);

    //该线程需要持有Socket
    @Getter
    private Socket socket;

    @Getter
    private String userName;

    //构造器可以接收一个Socket对象
    public ClientConnectServerThread(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
    }

    @Override
    public void run() {
        //因为Thread需要在后台和服务器通信，因此我们while循环
        while (Boolean.TRUE) {

            log.debug("客户端线程，等待读取从服务器端发送的消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //判断这个message类型，然后做相应的业务处理
                if (MessageType.MESSAGE_RET_ONLINE_FRIEND.equals(message.getMesType())) {
                    //取出在线列表信息
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("==========当前在线用户列表==========");
                    for (String onlineUser : onlineUsers) {
                        System.out.println("当前在线用户：" + onlineUser);
                    }
                } else if (MessageType.MESSAGE_CLIENT_EXIT.equals(message.getMesType())) {
                    socket.close();
                    break;
                } else if (MessageType.MESSAGE_COMM_MES.equals(message.getMesType())) {
                    System.out.println("\n" + message.getSender() + "对你说：" + message.getContent());
//                    System.out.println("\n" + message.getSender() + "对" + message.getReceiver() + "说：" + message.getContent());
                } else if (MessageType.MESSAGE_TO_ALL_MES.equals(message.getMesType())) {
                    System.out.println("\n" + message.getSender() + "对大家说：" + message.getContent());
                } else if (MessageType.MESSAGE_FILE_MES.equals(message.getMesType())) {
                    System.out.println("\n" + message.getSender() + "给你发送了一个文件");

                    //取出message的文件字节数组，通过文件输出流写到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("保存成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


}
