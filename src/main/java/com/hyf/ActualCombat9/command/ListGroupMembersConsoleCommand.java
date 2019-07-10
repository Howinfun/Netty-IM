package com.hyf.ActualCombat9.command;

import com.hyf.ActualCombat9.packet.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
public class ListGroupMembersConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入群聊ID：");
        String groupId = scanner.next();
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
