package com.hyf.ActualCombat2.packet;

import com.hyf.ActualCombat2.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
@Data
public class LoginRequestPacket extends Packet{

    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
