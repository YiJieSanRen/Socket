package com.lemur.chat.builder;

import com.lemur.chat.domain.Message;
import com.lemur.chat.utils.DateUtil;

import java.text.ParseException;
import java.util.Date;

public class ReceiverMessageBuilder implements MessageBuilder {

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

    public ReceiverMessageBuilder(String mesType) {
        this.mesType = mesType;
    }

    @Override
    public MessageBuilder setSender(String sender) {
        this.sender = sender;
        return this;
    }

    @Override
    public MessageBuilder setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    @Override
    public MessageBuilder setSendTime(String sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    @Override
    public MessageBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public Message build() {
        if (this.sender == null) {
            this.sender = "";
        }
        if (this.receiver == null) {
            this.receiver = "";
        }
        try {
            this.sendTime = DateUtil.parseDate(new Date()).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new Message(sender, receiver, mesType, content, sendTime);
    }
}
