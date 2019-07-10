package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
@Data
public class JoinGroupResponsePacket extends Packet{
    private boolean success;
    private String groupId;
    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
