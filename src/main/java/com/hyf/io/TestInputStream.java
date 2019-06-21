package com.hyf.io;

import cn.hutool.core.date.DateUtil;

import java.util.concurrent.CompletableFuture;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/18
 */
public class TestInputStream {
    public static void main(String[] args) throws Exception{
        /*InputStream inputStream = new FileInputStream("C:\\Users\\87623\\Desktop\\新建文本文档.txt");
        byte[] bytes = new byte[1024];
        int length;
        while ( (length = inputStream.read(bytes)) != -1){
            System.out.println(new String(bytes));
        }*/

        /*OutputStream outputStream = new FileOutputStream("C:\\Users\\87623\\Desktop\\1.txt");
        outputStream.write(123);
        outputStream.flush();
        outputStream.close();*/

        /*InputStream inputStream = new FileInputStream("C:\\Users\\87623\\Desktop\\新建文本文档.txt");
        Reader reader = new InputStreamReader(inputStream);
        int data = reader.read();
        while(data != -1){
            char theChar = (char) data;
            System.out.println(theChar);
            data = reader.read();
        }*/
        System.out.println(DateUtil.now());
        CompletableFuture.supplyAsync(()->1)
                .thenApply(i->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i+1;
                })
                .thenApply(i->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i*i;
                })
                .whenCompleteAsync((r,e)-> System.out.println(r));
        System.out.println(DateUtil.now());
    }
}
