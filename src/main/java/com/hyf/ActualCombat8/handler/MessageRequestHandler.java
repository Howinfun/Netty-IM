package com.hyf.ActualCombat8.handler;

import com.hyf.ActualCombat8.entity.Session;
import com.hyf.ActualCombat8.packet.MessageRequestPacket;
import com.hyf.ActualCombat8.packet.MessageResponsePacket;
import com.hyf.ActualCombat8.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        String msg = messageRequestPacket.getMessage();
        String toUserId = messageRequestPacket.getToUserId();
        Channel toChannel = SessionUtil.getChannel(toUserId);
        Session fromSession = SessionUtil.getSession(ctx.channel());

        // 发送消息到接收端
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        if (toChannel != null && SessionUtil.isLogin(toChannel)){
            responsePacket.setSucess(true);
            responsePacket.setFromUserId(fromSession.getUserId());
            responsePacket.setFromUserName(fromSession.getUserName());
            responsePacket.setMessage(msg);
            toChannel.writeAndFlush(responsePacket);
        }else{
            responsePacket.setSucess(false);
            responsePacket.setMessage("对方不在线，发送失败");
            ctx.channel().writeAndFlush(responsePacket);
        }

    }
}
