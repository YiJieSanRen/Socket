package com.lemur.socket.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端
 * 使用字符流方式读写
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

        //3.通过输出流，写入数据到 数据通道，使用字符流
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
//        outputStreamWriter.write();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello,server 我是客户端");
        bufferedWriter.newLine();//插入一个换行符，表示写入的内容结束，注意，要求对方使用readLine()!!!
        bufferedWriter.flush();//如果使用的字符流，需要手动刷新，否则数据不会写入数据通道

        //3.5 (重要)设置结束标记
        //socket.shutdownOutput();

        //4.接收消息
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String buf = bufferedReader.readLine();
        System.out.println("客户端接收到消息：" + buf);


        //5.关闭流对象和socket，必须关闭
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();

        System.out.println("客户端退出了！");
    }
}
