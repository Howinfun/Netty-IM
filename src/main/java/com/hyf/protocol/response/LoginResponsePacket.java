package com.hyf.protocol.response;

import com.hyf.protocol.Packet;
import com.hyf.protocol.comman.Command;
import lombok.Data;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
@Data
public class LoginResponsePacket extends Packet{

    private String userId;

    private String userName;

    private Boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
