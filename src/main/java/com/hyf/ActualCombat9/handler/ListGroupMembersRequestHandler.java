package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.TaskThreadPool;
import com.hyf.ActualCombat9.entity.Session;
import com.hyf.ActualCombat9.packet.ListGroupMembersRequestPacket;
import com.hyf.ActualCombat9.packet.ListGroupMembersResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        TaskThreadPool.INSTANCE.submit(()-> {
            // 遍历ChannelGroup，获取Session
            String groupId = msg.getGroupId();
            ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
            List<Session> sessionList = new ArrayList<>();
            ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
            if (!channelGroup.isEmpty()) {
                for (Channel channel : channelGroup) {
                    Session session = SessionUtil.getSession(channel);
                    sessionList.add(session);
                }
                responsePacket.setSuccess(true);
                responsePacket.setSessionList(sessionList);
            } else {
                responsePacket.setSuccess(false);
                responsePacket.setReason("群聊不存在");
            }
            // 响应客户端
            responsePacket.setGroupId(groupId);
            ctx.channel().writeAndFlush(responsePacket);
        });
    }
}
