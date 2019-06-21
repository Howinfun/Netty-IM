package com.hyf.nio.socket;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/21
 */
public class TestServerSocketChannel {
    public static void main(String[] args)throws Exception {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        // 监听端口号8080
        channel.socket().bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        // 注册到Selector中，ACCEPT操作
        channel.register(selector, SelectionKey.OP_ACCEPT);
        // handler处理
        ServerHandler handler = new ServerHandler();
        // 不断轮询Selector
        while (true){
            if (selector.select()>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    // 接收状态
                    if (key.isAcceptable()){
                        handler.handleAccept(key);
                    }
                    // 可读状态
                    if (key.isReadable()){
                        handler.handleRead(key);
                    }
                    // 处理过的key要移除掉
                    iterator.remove();
                }
            }
        }
    }
}
