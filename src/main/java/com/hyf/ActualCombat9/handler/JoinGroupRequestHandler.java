package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.TaskThreadPool;
import com.hyf.ActualCombat9.packet.JoinGroupNoticePacket;
import com.hyf.ActualCombat9.packet.JoinGroupRequestPacket;
import com.hyf.ActualCombat9.packet.JoinGroupResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        TaskThreadPool.INSTANCE.submit(()-> {
            // 根据群组ID查询ChannelGroup，然后加上当前channel
            String groupId = msg.getGroupId();
            ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
            if (channelGroup != null) {
                channelGroup.add(ctx.channel());

                // 响应客户端
                JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
                responsePacket.setSuccess(true);
                responsePacket.setGroupId(groupId);
                ctx.channel().writeAndFlush(responsePacket);

                // 响应其他客户端
                JoinGroupNoticePacket noticePacket = new JoinGroupNoticePacket();
                noticePacket.setOperate(1);
                noticePacket.setGroupId(groupId);
                noticePacket.setSession(SessionUtil.getSession(ctx.channel()));
                channelGroup.writeAndFlush(noticePacket, ChannelMatchers.isNot(ctx.channel()));
            } else {
                // 响应客户端
                JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
                responsePacket.setSuccess(false);
                responsePacket.setGroupId(groupId);
                responsePacket.setMessage("群组[" + groupId + "]不存在");
                ctx.channel().writeAndFlush(responsePacket);
            }
        });

    }
}
