package com.hyf.nio;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/21
 */
public class testFileChannel {

    @Test
    public void test() throws Exception{
        RandomAccessFile file = new RandomAccessFile("d:\\1.txt","rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        byteBuffer.clear();
        byteBuffer.put("你好世界".getBytes());
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            fileChannel.write(byteBuffer);

        }
        fileChannel.close();
    }

    @Test
    public void test1() throws Exception{
        RandomAccessFile file = new RandomAccessFile("d:\\1.txt","rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array(),0,byteBuffer.limit()));
    }
}
