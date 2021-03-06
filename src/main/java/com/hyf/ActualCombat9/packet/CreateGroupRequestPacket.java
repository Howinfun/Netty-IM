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
public class CreateGroupRequestPacket extends Packet{

    private List<String> userId;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
