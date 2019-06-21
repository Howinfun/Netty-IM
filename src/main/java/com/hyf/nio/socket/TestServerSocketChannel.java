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
        channel.socket().bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        // handler处理
        ServerHandler handler = new ServerHandler();
        while (true){
            if (selector.select()>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()){
                        handler.handleAccept(key);
                    }
                    if (key.isReadable()){
                        handler.handleRead(key);
                    }
                    iterator.remove();
                }
            }
        }
    }
}
