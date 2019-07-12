package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.TaskThreadPool;
import com.hyf.ActualCombat9.packet.JoinGroupNoticePacket;
import com.hyf.ActualCombat9.packet.QuitGroupRequestPacket;
import com.hyf.ActualCombat9.packet.QuitGroupResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    private QuitGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        TaskThreadPool.INSTANCE.submit(()-> {
            // 将当前channel从channelGroup中移除
            String groupId = msg.getGroupId();
            ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
            if (channelGroup != null) {
                channelGroup.remove(ctx.channel());
                // 如果群聊中没有人了，则将群聊信息从缓存中去除
                if (channelGroup.isEmpty()) {
                    SessionUtil.removeChannelGroup(groupId);
                    System.out.println("群聊[" + groupId + "]没有人员，从缓存中移除");
                }
                // 响应客户端
                QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
                responsePacket.setSuccess(true);
                responsePacket.setGroupId(groupId);
                ctx.channel().writeAndFlush(responsePacket);

                // 响应其他客户端
                JoinGroupNoticePacket noticePacket = new JoinGroupNoticePacket();
                noticePacket.setOperate(2);
                noticePacket.setGroupId(groupId);
                noticePacket.setSession(SessionUtil.getSession(ctx.channel()));
                channelGroup.writeAndFlush(noticePacket, ChannelMatchers.isNot(ctx.channel()));
            } else {
                // 响应客户端
                QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
                responsePacket.setSuccess(false);
                responsePacket.setGroupId(groupId);
                responsePacket.setMessage("群组[" + groupId + "]不存在");
                ctx.channel().writeAndFlush(responsePacket);
            }
        });
    }
}
