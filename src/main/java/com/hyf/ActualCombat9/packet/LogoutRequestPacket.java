package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class LogoutRequestPacket extends Packet{
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
