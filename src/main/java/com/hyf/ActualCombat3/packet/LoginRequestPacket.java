package com.hyf.ActualCombat3.packet;

import com.hyf.ActualCombat3.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
@Data
public class LoginRequestPacket extends Packet{

    private String userName;
    private String password;

    /**
     * 获取指令
     *
     * @return
     */
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
