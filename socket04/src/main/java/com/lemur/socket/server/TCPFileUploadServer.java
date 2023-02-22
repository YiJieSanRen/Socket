package com.lemur.socket.server;

import utils.StreamUtils;

import java.io.*;
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
            byte[] bytes = StreamUtils.streamToByteArray(bis);

            //4.将得到的 bytes 数组，写入到指定的路径，就得到一个文件了
            String destFilePath = "socket04\\src\\default.jpeg";
            creatFile(destFilePath);
            BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(destFilePath)));
            bos.write(bytes);
            bos.close();

            //5.返回信息
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("服务端 图片已保存");
            bufferedWriter.flush();
            socket.shutdownOutput();


            //6.关闭流和socket
            bufferedWriter.close();
            bis.close();
            socket.close();
            serverSocket.close();

            System.out.println("服务端退出了！");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文件路径创建文件夹
     *
     * @param destFilePath 文件路径（带后缀名）
     */
    private static void creatFile(String destFilePath) {
        //存在多重路径
        if (destFilePath.lastIndexOf(File.separator) != -1) {
            destFilePath = destFilePath.substring(0, destFilePath.lastIndexOf(File.separator));
            new File(destFilePath).mkdirs();
        }
    }
}
