package com.hyf.ActualCombat9.command;

import com.hyf.ActualCombat9.packet.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class SendToUserConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入用户ID和信息：");
        String toUserId = scanner.next();
        String msg = scanner.next();
        MessageRequestPacket requestPacket = new MessageRequestPacket();
        requestPacket.setToUserId(toUserId);
        requestPacket.setMessage(msg);
        channel.writeAndFlush(requestPacket);
    }
}
