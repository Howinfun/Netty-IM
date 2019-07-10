package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
@Data
public class ListGroupMembersRequestPacket extends Packet{

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
