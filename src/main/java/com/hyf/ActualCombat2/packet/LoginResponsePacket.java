package com.hyf.ActualCombat2.packet;

import com.hyf.ActualCombat2.command.Command;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
public class LoginResponsePacket extends Packet{
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
