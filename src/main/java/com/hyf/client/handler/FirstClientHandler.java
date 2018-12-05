package com.hyf.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf buffer = getByteBuf(ctx);
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        // 获取二进制抽象ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        // 准备数据，指定字符串的字符集为utf-8
        byte[] bytes = "你好，我在学习Netty呢".getBytes(Charset.forName("utf-8"));
        // 填充数据到ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }
}
