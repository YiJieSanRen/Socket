package com.lemur.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 客户端
 * 记住结束标志
 */
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

        //3.5 (重要)设置结束标记
        socket.shutdownOutput();

        //4.接收消息
        byte[] buf = new byte[1024];
        int readLen;
        InputStream inputStream = socket.getInputStream();
        while ((readLen = inputStream.read(buf)) != -1) {
            System.out.println("客户端接收到消息：" + new String(buf, 0, readLen, StandardCharsets.UTF_8));
        }

        //5.关闭流对象和socket，必须关闭
        inputStream.close();
        outputStream.close();
        socket.close();

        System.out.println("客户端退出了！");
    }
}
