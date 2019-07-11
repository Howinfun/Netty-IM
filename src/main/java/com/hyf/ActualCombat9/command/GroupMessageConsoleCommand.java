package com.hyf.ActualCombat9.command;

import com.hyf.ActualCombat9.packet.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
public class GroupMessageConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入群聊ID 信息：");
        String groupId = scanner.next();
        String message = scanner.next();
        GroupMessageRequestPacket requestPacket = new GroupMessageRequestPacket();
        requestPacket.setGroupId(groupId);
        requestPacket.setMessage(message);
        channel.writeAndFlush(requestPacket);
    }
}
