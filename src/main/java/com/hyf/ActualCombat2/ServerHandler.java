package com.hyf.ActualCombat2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf receive = (ByteBuf) msg;

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        return ctx.alloc().ioBuffer(2048);
    }
}
