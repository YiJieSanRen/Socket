package com.lemur.socket.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        //1.连接服务器(ip,端口)
        //连接本机的9001端口，如果连接成功，返回一个Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 9001);
        //连接远程服务器的9001端口，如果连接成功，返回一个Socket对象
        //Socket socket = new Socket(InetAddress.getByName("47.100.46.162"), 9001);
        System.out.println("客户端 socket返回：" + socket.getClass());

        //2.连接上后，生成Socket，通过Socket.getOutputStream()得到 和 socket对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();

        //3.通过输出流，写入数据到 数据通道
        outputStream.write("hello,Lemur!".getBytes());

        //4.关闭流对象和socket，必须关闭
        outputStream.close();
        socket.close();

        System.out.println("客户端退出了！");
    }
}