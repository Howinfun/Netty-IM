package com.hyf.server.handler;

import com.hyf.protocol.request.MessageRequestPacket;
import com.hyf.protocol.response.MessageResponsePacket;
import com.hyf.server.session.Session;
import com.hyf.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 根据当前通道获取当前用户信息（发送消息的用户）
        Session session = SessionUtil.getSession(ctx.channel());

        // 获取发送消息的信息
        String toUserId = messageRequestPacket.getToUserId();
        String message = messageRequestPacket.getMessage();
        // 根据被发送消息的用户id获取用户的channel，进行发送消息
        Channel channel = SessionUtil.getChannel(toUserId);

        // 组装消息响应包
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFormUserName(session.getUserName());
        messageResponsePacket.setMessage(message);

        // 发送消息
        channel.writeAndFlush(messageResponsePacket);
    }
}
