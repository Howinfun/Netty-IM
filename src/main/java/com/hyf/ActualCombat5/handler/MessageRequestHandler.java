package com.hyf.ActualCombat5.handler;

import cn.hutool.core.date.DateUtil;
import com.hyf.ActualCombat5.packet.MessageRequestPacket;
import com.hyf.ActualCombat5.packet.MessageResponsePacket;
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
        String requestMsg = messageRequestPacket.getMessage();
        System.out.println(DateUtil.now()+" 收到客户端消息："+requestMsg);
        // 反馈消息
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage("服务端回复【"+requestMsg+"】");
        ctx.channel().writeAndFlush(responsePacket);
    }
}
