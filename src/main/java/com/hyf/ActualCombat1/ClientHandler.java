package com.hyf.ActualCombat1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/26
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            ByteBuf byteBuf = getByteBuf(ctx);
            String content = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!";
            System.out.println("客户端写出消息->"+content);
            ctx.writeAndFlush(byteBuf.writeBytes(content.getBytes(Charset.forName("utf-8"))));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端接收到消息->"+byteBuf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf byteBuf = ctx.alloc().buffer();
        return byteBuf;
    }
}
