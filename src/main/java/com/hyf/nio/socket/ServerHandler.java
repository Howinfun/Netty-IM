package com.hyf.nio.socket;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Howinfun
 * @desc 服务器的通信处理
 * @date 2019/6/21
 */
public class ServerHandler {
    public  void handleAccept(SelectionKey key) throws Exception{
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
        // 获取客户端链接，并注册到Selector中
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        // 讲通道注册到Selector里头，然后设置为读操作，第三个参数是将你需要带的东西，可通过key获取
        clientChannel.register(key.selector(),SelectionKey.OP_READ,ByteBuffer.allocate(48));
    }

    public void handleRead(SelectionKey key) throws Exception{
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.clear();
        long byteRead = clientChannel.read(byteBuffer);
        if (byteRead == -1){
            clientChannel.close();
        }else{
            // 读取客户端发送的消息，并做出相应返回对应的消息
            byteBuffer.flip();
            String receiveMsg = new String(byteBuffer.array(),0,byteBuffer.limit());
            System.out.println("接收来自"+clientChannel.socket().getRemoteSocketAddress()+"的消息："+receiveMsg);
            // 返回回应给客户端
            String sendMsg = "你好客户端，我已经接收到你的信息";
            byteBuffer.clear();
            byteBuffer.put(sendMsg.getBytes());
            byteBuffer.flip();
            clientChannel.write(byteBuffer);
            // 关闭通道 PS：千万不要关闭通道，不然客户端那边就被关闭掉了。。
            //clientChannel.close();
        }

    }
}
