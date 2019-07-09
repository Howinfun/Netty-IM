package com.hyf.ActualCombat8.packet;

import com.hyf.ActualCombat8.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;
    private String fromUserName;
    private String message;
    private boolean sucess;

    /**
     * 获取指令
     *
     * @return
     */
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
