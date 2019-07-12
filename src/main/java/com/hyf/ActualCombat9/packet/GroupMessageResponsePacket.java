package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import com.hyf.ActualCombat9.entity.Session;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
@Data
public class GroupMessageResponsePacket extends Packet{

    private boolean success;
    private String fromGroupId;
    private Session fromUser;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
