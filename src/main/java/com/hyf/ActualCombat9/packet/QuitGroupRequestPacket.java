package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
@Data
public class QuitGroupRequestPacket extends Packet{
    private String groupId;
    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
