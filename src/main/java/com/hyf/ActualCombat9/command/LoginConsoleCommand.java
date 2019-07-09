package com.hyf.ActualCombat9.command;

import com.hyf.ActualCombat9.packet.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class LoginConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.print("请输入用户名：");
        String userName = scanner.nextLine();
        // 创建登录对象
        loginRequestPacket.setUserName(userName);
        loginRequestPacket.setPassword("123456");

        // 写数据
        channel.writeAndFlush(loginRequestPacket);
    }
}
