package com.hyf.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/10
 */
public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1",8000);
        while(true){
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write((new Date()+" hello world").getBytes());
            Thread.sleep(2000);
        }
    }
}
