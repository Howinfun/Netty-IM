package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
@Data
public class CreateGroupResponsePacket extends Packet{

    private boolean success;
    private String groupId;
    private List<String> userNames;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
