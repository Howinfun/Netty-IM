package com.hyf.nio.dataGramChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/21
 */
public class TestDataGramChannel2 {
    public static void main(String[] args) {
        new Thread(()->{
            DatagramChannel channel;
            try {
                channel = DatagramChannel.open();
                channel.configureBlocking(false);
                ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                byteBuffer.clear();
                byteBuffer.put("hello world".getBytes());
                byteBuffer.flip();
                channel.send(byteBuffer,new InetSocketAddress("127.0.0.1",8080));
                byteBuffer.clear();
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
