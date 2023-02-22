package com.lemur.socket.client;

import utils.StreamUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 客户端
 * 使用字符流方式读写
 */
public class TCPFileUploadClient {
    public static void main(String[] args) throws IOException {
        //1.连接服务器(ip,端口)
        Socket socket = new Socket(InetAddress.getLocalHost(), 9001);

        //2.接收用户输入，指定下载文件名
        Scanner scanner = new Scanner(System.in);
        String downloadedFileName = scanner.next();

        byte[] bytes = downloadedFileName.getBytes();

        //3.连接上后，生成Socket，通过Socket.getOutputStream()得到 和 socket对象关联的输出流对象
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        //将文件对应的字节数组的内容写入到数据通道
        bos.write(bytes);
        //设置写入结束的标志
        socket.shutdownOutput();

        //4.接收客户端传来的消息
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        bytes = StreamUtils.streamToByteArray(bis);

        //5.得到一个输出流，准备将bytes写入到磁盘文件
        String filePath = "socket07\\" + downloadedFileName + ".mp3";
        bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)));
        bos.write(bytes);

        //4.关闭流对象和socket，必须关闭
        bis.close();
        bos.close();
        socket.close();

        System.out.println("客户端退出了！");
    }
}
