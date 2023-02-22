package com.lemur.chat.domain;

/**
 * 通信消息构造接口
 */
public interface MessageBuilder {

    //发送方
    MessageBuilder setSender(String sender);

    //接收方
    MessageBuilder setReceiver(String receiver);

    //发送时间
    MessageBuilder setSendTime(String sendTime);

    //内容
    MessageBuilder setContent(String content);

    //构建Message对象
    Message build();
}
