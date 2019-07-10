package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.packet.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()){
            System.out.println("退出群["+msg.getGroupId()+"]成功");
        }
    }
}
