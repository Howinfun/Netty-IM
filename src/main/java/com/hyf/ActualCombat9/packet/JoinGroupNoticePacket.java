package com.hyf.ActualCombat9.packet;

import com.hyf.ActualCombat9.command.Command;
import com.hyf.ActualCombat9.entity.Session;
import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
@Data
public class JoinGroupNoticePacket extends Packet{

    /** 1是加入 2是退出 */
    private Integer operate;
    private String groupId;
    private Session session;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_NOTICE;
    }
}
