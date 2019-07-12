package com.hyf.ActualCombat9.handler.server;

import com.hyf.ActualCombat9.TaskThreadPool;
import com.hyf.ActualCombat9.entity.Session;
import com.hyf.ActualCombat9.packet.GroupMessageRequestPacket;
import com.hyf.ActualCombat9.packet.GroupMessageResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        TaskThreadPool.INSTANCE.submit(()-> {
            String groupId = msg.getGroupId();
            String message = msg.getMessage();
            ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
            GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
            // 先判断用户是否还在群里头
            if (channelGroup.contains(ctx.channel())) {
                Session fromUser = SessionUtil.getSession(ctx.channel());
                // 给群里所有的人发送响应
                responsePacket.setSuccess(true);
                responsePacket.setFromGroupId(groupId);
                responsePacket.setFromUser(fromUser);
                responsePacket.setMessage(message);
                channelGroup.writeAndFlush(responsePacket);
            } else {
                // 只给当前用户发送响应
                responsePacket.setSuccess(false);
                responsePacket.setMessage("你已不在此群聊中，发送失败");
                ctx.channel().writeAndFlush(responsePacket);
            }
        });
    }
}
