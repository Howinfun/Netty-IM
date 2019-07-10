package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import com.hyf.ActualCombat9.entity.Session;
import lombok.Data;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
