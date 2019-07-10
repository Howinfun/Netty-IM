package com.hyf.ActualCombat9.command;

import com.hyf.ActualCombat9.packet.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入群聊ID");
        String groupId = scanner.next();
        JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
