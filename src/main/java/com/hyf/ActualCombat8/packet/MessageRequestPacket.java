package com.hyf.ActualCombat8.packet;

import com.hyf.ActualCombat8.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
@Data
public class MessageRequestPacket extends Packet {

    private String toUserId;
    private String message;

    /**
     * 获取指令
     *
     * @return
     */
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
