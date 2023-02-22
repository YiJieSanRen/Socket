package com.lemur.chat.service.impl;

import com.lemur.chat.builder.SendMessageBuilder;
import com.lemur.chat.domain.Message;
import com.lemur.chat.domain.MessageType;
import com.lemur.chat.service.BaseService;

import java.io.File;
import java.io.FileInputStream;

/**
 * 文件发送
 */
public class FileClientService extends BaseService {

    /**
     * 发送文件给指定用户
     *
     * @param src      源地址
     * @param dest     目的地址
     * @param sender   发送者
     * @param receiver 接收者
     */
    public void sendFileToOne(String src, String dest, String sender, String receiver) {
        this.sender = sender;

        //提取需要上传的文件
        int fileLen = (int) new File(src).length();
        byte[] fileBytes = new byte[fileLen];

        try (FileInputStream fileInputStream = new FileInputStream(src)) {
            int read = fileInputStream.read(fileBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //构建用户
        Message message = new SendMessageBuilder(MessageType.MESSAGE_FILE_MES).setFileBytes(fileBytes).setFileLen(fileLen).setSender(sender).setReceiver(receiver).setSrc(src).setDest(dest).build();

        System.out.println("\n用户" + sender + "给用户" + receiver + "发送文件" + src + "到" + dest);
        //发送给服务端
        sendMess(message);
    }

}
