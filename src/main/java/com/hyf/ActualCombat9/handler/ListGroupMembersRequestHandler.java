package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.entity.Session;
import com.hyf.ActualCombat9.packet.ListGroupMembersRequestPacket;
import com.hyf.ActualCombat9.packet.ListGroupMembersResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        // 遍历ChannelGroup，获取Session
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }
        // 响应客户端
        ListGroupMembersResponsePacket requestPacket = new ListGroupMembersResponsePacket();
        requestPacket.setGroupId(groupId);
        requestPacket.setSessionList(sessionList);
        ctx.channel().writeAndFlush(requestPacket);
    }
}
