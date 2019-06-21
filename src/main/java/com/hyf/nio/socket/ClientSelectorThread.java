package com.hyf.nio.socket;

import lombok.AllArgsConstructor;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/21
 */
@AllArgsConstructor
public class ClientSelectorThread implements Runnable{
    private Selector selector;

    @Override
    public void run() {
        try {
            ClientHandler clientHandler = new ClientHandler();
            while(true){
                if (selector.select()>0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if (key.isReadable()){
                            clientHandler.handleRead(key);
                        }

                        iterator.remove();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
