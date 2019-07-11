package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
@Data
public class GroupMessageRequestPacket extends Packet{
    private String groupId;
    private String message;
    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
