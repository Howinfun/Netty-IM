package com.hyf.ActualCombat9.command;

import com.hyf.ActualCombat9.packet.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class CreateGroupConsoleCommand implements ConsoleCommand{
    private static final String USER_ID_SPLITER = ",";
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("【拉人群聊】输入userId列表，userId之间英文逗号隔开：");
        String userIds = scanner.next();
        CreateGroupRequestPacket groupRequestPacket = new CreateGroupRequestPacket();
        groupRequestPacket.setUserId(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(groupRequestPacket);
    }
}
