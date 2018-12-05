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
public class MessageResponsePacket extends Packet {

    private String fromUserId;
    private String formUserName;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

}
