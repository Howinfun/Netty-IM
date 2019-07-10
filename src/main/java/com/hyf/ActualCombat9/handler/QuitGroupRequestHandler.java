package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.packet.JoinGroupNoticePacket;
import com.hyf.ActualCombat9.packet.QuitGroupRequestPacket;
import com.hyf.ActualCombat9.packet.QuitGroupResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        // 将当前channel从channelGroup中移除
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());
        // 如果群聊中没有人了，则将群聊信息从缓存中去除
        if (channelGroup.isEmpty()){
            SessionUtil.removeChannelGroup(groupId);
            System.out.println("群聊["+groupId+"]没有人员，从缓存中移除");
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
    }
}
