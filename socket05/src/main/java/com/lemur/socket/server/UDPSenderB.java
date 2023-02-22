package com.lemur.socket.server;

import java.io.IOException;
import java.net.*;

/**
 * 发送端
 */
public class UDPSenderB {
    public static void main(String[] args) throws IOException {
        //1.创建 DatagramSocket 对象，准备在9000端口接收数据
        DatagramSocket socket = new DatagramSocket(9000);

        //2.将需要发送的数据，封装到 DatagramPacket对象
        byte[] bytes = "hello 明天吃火锅~".getBytes();

        //封装的 DatagramPacket对象 data 内容字节数组,data.length,主机(IP),端口
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("10.168.4.183"), 9001);
        socket.send(packet);

        socket.receive(packet);

        //4.可以把packet 进行拆包，取出数据，并显示
        int length = packet.getLength(); //实际接收到的数据字节长度
        byte[] data = packet.getData();
        String s = new String(data, 0, length);
        System.out.println(s);

        socket.close();
    }
}
