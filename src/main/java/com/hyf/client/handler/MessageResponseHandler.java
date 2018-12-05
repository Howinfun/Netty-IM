package com.hyf.client.handler;

import com.hyf.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {

        // 消息响应包
        String formUserId = messageResponsePacket.getFromUserId();
        String formUserName = messageResponsePacket.getFormUserName();
        String message = messageResponsePacket.getMessage();
        // 打印消息
        System.out.println(formUserId+":"+formUserName+" -> "+message);
    }
}
