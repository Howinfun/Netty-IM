package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.entity.Session;
import com.hyf.ActualCombat9.packet.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        String fromGroupId = msg.getFromGroupId();
        String message = msg.getMessage();
        Session fromUser = msg.getFromUser();
        System.out.println("收到来自群["+fromGroupId+"]中"+fromUser+"发来的消息："+message);
    }
}
