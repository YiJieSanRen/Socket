package com.lemur.socket.utils;

import java.io.*;
import java.util.Objects;

public class StreamUtils {

    /**
     * 功能：将输入流转换成byte[]，即可以把文件的内容读入到byte[]
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] streamToByteArray(InputStream is) throws IOException {
        //创建输出流对象
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //字节数组，单次读取大小
        byte[] array = new byte[1024];
        int len;

        //循环读取
        while ((len = is.read(array)) != -1) {
            //把读取到的数据，写入bos
            bos.write(array, 0, len);
        }
        //将读取到的内容转成字节数组
        array = bos.toByteArray();
        bos.close();
        return array;
    }


    public static String streamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        if (Objects.nonNull(line = reader.readLine())) {
            sb.append(line).append("\r\n");
        }
        return sb.toString();
    }

}
