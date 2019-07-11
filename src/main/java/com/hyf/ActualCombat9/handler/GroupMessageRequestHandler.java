package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.entity.Session;
import com.hyf.ActualCombat9.packet.GroupMessageRequestPacket;
import com.hyf.ActualCombat9.packet.GroupMessageResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        String message = msg.getMessage();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        Session fromUser = SessionUtil.getSession(ctx.channel());
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setFromUser(fromUser);
        responsePacket.setMessage(message);
        channelGroup.writeAndFlush(responsePacket);
    }
}
