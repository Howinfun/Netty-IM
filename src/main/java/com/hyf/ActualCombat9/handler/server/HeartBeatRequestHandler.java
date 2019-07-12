package com.hyf.ActualCombat9.handler.server;

import com.hyf.ActualCombat9.packet.HeartBeatRequestPacket;
import com.hyf.ActualCombat9.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/12
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();
    private HeartBeatRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        // 可直接使用ctx.writeAndFlush，因为不必经过后续的ChannelInboundHandler和ChannelOutboundHandler
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
