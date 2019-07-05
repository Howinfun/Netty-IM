package com.hyf.ActualCombat6.handler;

import com.hyf.ActualCombat6.packet.MessageResponsePacket;
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
        if (messageResponsePacket.isSucess()){
            System.out.println(messageResponsePacket.getFromUserId()+"："+messageResponsePacket.getFromUserName()+"->"+messageResponsePacket.getMessage());
        }else{
            System.out.println(messageResponsePacket.getMessage());
        }
    }
}
