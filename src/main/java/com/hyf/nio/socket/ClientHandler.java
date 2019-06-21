package com.hyf.nio.socket;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/21
 */
public class ClientHandler {

    public void handleRead(SelectionKey key) throws Exception{
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.clear();
        long byteRead = clientChannel.read(byteBuffer);
        if (byteRead == -1){
            clientChannel.close();
        }else{
            byteBuffer.flip();
            String receiveMsg = new String(byteBuffer.array(),0,byteBuffer.limit());
            System.out.println("接收来自服务器的消息："+receiveMsg);
        }
    }
}
