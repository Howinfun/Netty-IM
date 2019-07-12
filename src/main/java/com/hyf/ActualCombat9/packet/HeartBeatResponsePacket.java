package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/12
 */
public class HeartBeatResponsePacket extends Packet{
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
