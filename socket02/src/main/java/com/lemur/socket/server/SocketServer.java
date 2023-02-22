package com.lemur.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 服务端
 * 记住结束标志
 */
public class SocketServer {

    public static void main(String[] args) {
        try {
            //1.在本机的 9001 端口监听，等待连接
            //注意：端口不要被占用
            //细节：这个 ServerSocket 可以通过 accept() 返回多个Socket[多个客户端连接服务器的并发]
            ServerSocket serverSocket = new ServerSocket(9001);
            System.out.println("服务端，在9001端口监听，等待连接....");

            //2.当没有客户端连接9001端口时，程序会进入 阻塞 状态，等待连接...
            //  如果有客户端连接，则会返回一个Socket对象，程序继续
            Socket socket = serverSocket.accept();
            System.out.println("服务端 socket=" + socket.getClass());

            //3.通过socket.getInputStream() 读取
            //  客户端写入到数据通道的数据，显示
            InputStream inputStream = socket.getInputStream();
            //4.IO读取
            byte[] buf = new byte[1024];
            int readLen;
            while ((readLen = inputStream.read(buf)) != -1) {
                System.out.println("服务端接收到消息：" + new String(buf, 0, readLen, StandardCharsets.UTF_8));
            }

            //5.返回信息
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("Hello,limu!".getBytes());

            //*5.5 (重要)设置结束标记
            socket.shutdownOutput();

            //6.关闭流和socket
            outputStream.close();
            inputStream.close();
            socket.close();
            serverSocket.close();

            System.out.println("服务端退出了！");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
