package com.hyf.nio.dataGramChannel;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/21
 */
public class TestDatagramChannel {

    public static void main(String[] args)throws  Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.clear();
        DatagramChannel channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(8080));
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        while (selector.select()>0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.receive(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array(),0,buffer.limit()));
                    buffer.clear();
                }
            }
            iterator.remove();
        }

    }
}
