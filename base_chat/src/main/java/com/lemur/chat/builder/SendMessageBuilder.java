package com.lemur.chat.builder;

import com.lemur.chat.domain.Message;
import com.lemur.chat.utils.DateUtil;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.text.ParseException;
import java.util.Date;

@Setter
@Accessors(chain = true)
public class SendMessageBuilder implements MessageBuilder {

    //发送方
    private String sender;

    //接收方
    private String receiver;

    //消息类型
    private String mesType;

    //内容
    private String content;

    //发送时间

    private String sendTime;

    //收发文件
    private byte[] fileBytes;

    //文件大小
    private int fileLen = 0;

    //文件目的地址
    private String dest;

    //文件源地址
    private String src;

    public SendMessageBuilder(String mesType) {
        this.mesType = mesType;
    }

    @Override
    public Message build() {
        try {
            this.sendTime = DateUtil.parseDate(new Date()).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new Message(sender, receiver, mesType, content, sendTime, fileBytes, fileLen, dest, src);
    }
}
