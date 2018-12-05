package com.hyf.protocol.request;

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
public class LoginRequestPacket extends Packet {

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
