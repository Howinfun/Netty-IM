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
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 将接收到的消息打印出来
        ByteBuf byteBuf = (ByteBuf) msg;
        String receive = byteBuf.toString(Charset.forName("utf-8"));
        System.out.println("服务端接收到数据->"+receive);

        // 给客户端响应
        /*String content = "你好，欢迎连接此服务器";
        System.out.println("客户端写出消息->"+content);
        ByteBuf response = getByteBuf(ctx).writeBytes(content.getBytes(Charset.forName("utf-8")));
        ctx.writeAndFlush(response);*/
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf byteBuf = ctx.alloc().buffer();
        return byteBuf;
    }
}
