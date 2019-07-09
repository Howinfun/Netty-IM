package com.hyf.ActualCombat9.command;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Howinfun
 * @desc 控制台命令管理 -> 单例
 * @date 2019/7/9
 */
public class ConsoleCommandManager implements ConsoleCommand{

    private static final Map<String,ConsoleCommand> consoleCommandMap = new HashMap<>(5);


    private static ConsoleCommandManager instance = new ConsoleCommandManager();

    private ConsoleCommandManager(){
        consoleCommandMap.put("sendToUser",new SendToUserConsoleCommand());
        consoleCommandMap.put("createGroup",new CreateGroupConsoleCommand());
        consoleCommandMap.put("logout",new LogoutConsoleCommand());
    }

    public static ConsoleCommandManager getInstance(){
        return instance;
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (consoleCommand != null){
            consoleCommand.exec(scanner,channel);
        }else{
            System.out.println("无法识别["+command+"]命令");
        }
    }
}
