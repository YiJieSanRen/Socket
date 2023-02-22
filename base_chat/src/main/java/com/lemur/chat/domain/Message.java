package com.lemur.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通信消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
