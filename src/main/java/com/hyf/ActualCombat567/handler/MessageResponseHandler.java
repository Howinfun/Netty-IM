package com.hyf.ActualCombat567.handler;

import cn.hutool.core.date.DateUtil;
import com.hyf.ActualCombat567.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String responseMsg = messageResponsePacket.getMessage();
        System.out.println(DateUtil.now()+"收到服务端消息："+responseMsg);
    }
}
