package com.hyf.TestPipelineAndChannelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        byteBuf.writeBytes("你好".getBytes(Charset.forName("utf-8")));
        ctx.channel().writeAndFlush(byteBuf);
    }
}
