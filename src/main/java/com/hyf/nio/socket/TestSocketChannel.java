package com.hyf.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/21
 */
public class TestSocketChannel {
    private static boolean flag = true;
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        Thread thread = new Thread(new ClientSelectorThread(selector));
        thread.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    while (flag) {
                        System.out.print("请输入：");
                        Scanner scan = new Scanner(System.in);//键盘输入数据
                        String msg = scan.next();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                        byteBuffer.clear();
                        byteBuffer.put(msg.getBytes());
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                    }
                } catch (IOException e) {
                    flag = false;
                    e.printStackTrace();
                } finally {
                    flag = false;
                }
            }
        }.start();

    }
}
