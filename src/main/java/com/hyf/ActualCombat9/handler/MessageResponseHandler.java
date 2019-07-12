package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();

    private MessageResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        if (messageResponsePacket.isSucess()){
            System.out.println(messageResponsePacket.getFromUserId()+"："+messageResponsePacket.getFromUserName()+"->"+messageResponsePacket.getMessage());
        }else{
            System.out.println(messageResponsePacket.getMessage());
        }
    }
}
