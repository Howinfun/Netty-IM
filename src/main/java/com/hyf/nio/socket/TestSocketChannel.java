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
 * @desc 客户端
 * @date 2019/6/21
 */
public class TestSocketChannel {
    private static boolean flag = true;
    public static void main(String[] args) throws Exception{
        // 打开一个客户端通道
        SocketChannel socketChannel = SocketChannel.open();
        // 网络连接
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        // 往selector里注册读操作，客户端往后的读操作都由selector去搞定
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 启动一个线程去处理客户端的消息接收
        Thread thread = new Thread(new ClientSelectorThread(selector));
        thread.start();
        new Thread(()->{
            try {
                // 可循环输入，读取控制台的输入
                while (flag) {
                    System.out.print("请输入：");
                    //键盘输入数据
                    Scanner scan = new Scanner(System.in);
                    String msg = scan.next();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(48);
                    byteBuffer.clear();
                    byteBuffer.put(msg.getBytes());
                    byteBuffer.flip();
                    // 给服务器写消息
                    socketChannel.write(byteBuffer);
                }
            } catch (IOException e) {
                // 如果报错则断开循环输入
                flag = false;
            } finally {
                //最后关闭资源
                try {
                    socketChannel.close();
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
