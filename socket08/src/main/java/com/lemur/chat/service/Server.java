package com.lemur.chat.service;

import com.lemur.chat.builder.ReceiverMessageBuilder;
import com.lemur.chat.domain.MessageType;
import com.lemur.chat.domain.User;
import com.lemur.chat.service.impl.UserServiceImpl;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这是服务器，在监听9001，等待客户端的连接，并保持通信
 */
public class Server {
    Log log = LogFactory.getLog(Server.class);
    private ServerSocket ss = null;

    private final UserService userService = new UserServiceImpl();


    public Server() {
        //注：端口可以写在配置文件中。
        try {
            log.debug("服务端在9001端口监听");
            ss = new ServerSocket(9001);
            while (Boolean.TRUE) { //当和某个客户端连接后，会继续监听
                Socket socket = ss.accept();

                //得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                //得到socket关联的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                User user = (User) ois.readObject();

                //验证
                if (userService.checkUser(user)) {

                    //将message对象回复给客户端
                    oos.writeObject(new ReceiverMessageBuilder(MessageType.MESSAGE_LOGIN_SUCCESS).build());
                    //创建一个线程，和客户端保持通信，该线程需要持有socket对象
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, user.getUserName());
                    //启动该线程
                    serverConnectClientThread.start();

                    //把该线程对象，放入到一个集合中，进行管理
                    ManageServerConnectClientThread.addClientConnectServerThread(user.getUserName(), serverConnectClientThread);
                } else {
                    //验证失败
                    log.debug("用户 " + user.getUserName() + " 验证失败");
                    oos.writeObject(new ReceiverMessageBuilder(MessageType.MESSAGE_LOGIN_FAIL).build());
                    //关闭socket
                    socket.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //如果服务器退出了while，说明服务器端不再监听，因此要关闭ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
