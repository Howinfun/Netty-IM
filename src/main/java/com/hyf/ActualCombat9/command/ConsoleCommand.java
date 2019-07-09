package com.hyf.ActualCombat9.command;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public interface ConsoleCommand {
    /** 执行控制台命令 */
    void exec(Scanner scanner, Channel channel);
}
