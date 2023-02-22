package com.lemur.chat.service.impl;

import com.lemur.chat.domain.Message;
import com.lemur.chat.domain.MessageType;
import com.lemur.chat.builder.SendMessageBuilder;
import com.lemur.chat.domain.User;
import com.lemur.chat.service.BaseService;
import com.lemur.chat.service.ClientConnectServerThread;
import com.lemur.chat.service.ManageClientConnectServerThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 完成用户登录验证和用户注册等功能
 */
public class UserClientService extends BaseService {

    private final User user = new User();

    private Socket socket;

    /**
     * 根据userId 和 pwd 到服务器验证该用户是否合法
     *
     * @param userName
     * @param pwd
     * @return
     */
    public boolean checkUser(String userName, String pwd) {

        boolean flag = Boolean.FALSE;

        //创建User对象
        user.setUserName(userName);
        user.setPasswd(pwd);


        try {
            //连接到服务端，发送user对象
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9001);
            //得到ObjectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);

            //读取从服务器回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();
            if (MessageType.MESSAGE_LOGIN_SUCCESS.equals(message.getMesType())) {
                this.sender = user.getUserName();

                //创建一个和服务器端保持通信的线程 -> 创建一个类 ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket, user.getUserName());
                //启动客户端的线程
                clientConnectServerThread.start();
                //为了后面客户端的扩展，将线程放入到集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(userName, clientConnectServerThread);

                flag = Boolean.TRUE;
            } else {
                //如果登录失败，我们就不能启动和服务器通信的线程，关闭socket
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * 向服务器端请求在线用户列表
     */
    public void onlineFriendList() {
        //发送一个Message,类型MESSAGE_GET_ONLINE_FRIEND
        Message message = new SendMessageBuilder(MessageType.MESSAGE_GET_ONLINE_FRIEND).setSender(user.getUserName()).build();
        sendMess(message);
    }

    /**
     * 向服务器端发送退出系统的message对象
     */
    public void logout() {
        Message message = new SendMessageBuilder(MessageType.MESSAGE_CLIENT_EXIT).setSender(user.getUserName()).build();
        sendMess(message);
        System.exit(0);
    }
}
