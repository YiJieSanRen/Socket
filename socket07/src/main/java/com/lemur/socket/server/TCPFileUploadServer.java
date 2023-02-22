package com.lemur.socket.server;

import utils.StreamUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 服务端
 * 使用字符流方式读写
 */
public class TCPFileUploadServer {

    public static void main(String[] args) {
        try {
            //1.在本机的 9001 端口监听，等待连接
            ServerSocket serverSocket = new ServerSocket(9001);
            System.out.println("服务端，在9001端口监听，等待连接....");

            //2.等待连接...
            Socket socket = serverSocket.accept();

            //3.通过socket.getInputStream() 读取
            //  客户端写入到数据通道的数据，显示
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            String s = StreamUtils.streamToString(bis);
            String destFilePath;
            if ("好久不见".equals(s)) {
                destFilePath = "socket07\\src\\file\\好久不见.mp3";
            } else {
                destFilePath = "socket07\\src\\file\\My Sunshine.mp3";
            }

            //4.创建一个输入流，读取文件
            bis = new BufferedInputStream(Files.newInputStream(Paths.get(destFilePath)));

            //5.使用工具类StreamUtils，读取文件到一个字节数组
            byte[] bytes = StreamUtils.streamToByteArray(bis);

            //6.得到Socket关联的输出流
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            //7.将文件对应的字节数组的内容写入到数据通道
            bos.write(bytes);
            socket.shutdownOutput();

            //8.关闭相关资源
            bos.close();
            bis.close();
            socket.close();
            serverSocket.close();

            System.out.println("服务端退出了！");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
