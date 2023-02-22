package com.lemur.socket.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 接收端
 */
public class UDPReceiverA {
    public static void main(String[] args) throws IOException {
        //1.创建一个 DatagramSocket 对象，准备在9001端口接收
        DatagramSocket socket = new DatagramSocket(9001);

        //2.构建一个 DatagramPacket 对象，准备接收数据
        // 一个数据包最大为 64K
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        //3.调用 接收方法，将通过网络传输的 DatagramPacket 对象填充到packet对象
        // 当有数据包发送到 本机的9001端口时，就会接收到数据
        // 如果没有数据报发送到 本机的9001端口，就会阻塞 等待
        System.out.println("接收端A 等待接收数据..");
        socket.receive(packet);

        //4.可以把packet 进行拆包，取出数据，并显示
        int length = packet.getLength(); //实际接收到的数据字节长度
        byte[] data = packet.getData();
        String s = new String(data, 0, length);
        System.out.println(s);

        //5.返回一条消息
        byte[] bytes = "好的，明天见！".getBytes();

        packet = new DatagramPacket(bytes, 0, bytes.length, InetAddress.getByName("10.168.4.183"), 9000);
        socket.send(packet);

        socket.close();
    }
}
