package com.hyf.ActualCombat2.packet;

import com.hyf.ActualCombat2.command.Command;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
@Data
public class LoginResponsePacket extends Packet{

    /** 是否登录成功 */
    private boolean success = true;
    /** 如果失败，失败的原因 */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
