package com.hyf.ActualCombat4.packet;

import com.hyf.ActualCombat4.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

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
