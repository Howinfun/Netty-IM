package com.hyf.TestPipelineAndChannelHandler;

import cn.hutool.core.date.DateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

import java.nio.charset.Charset;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class InBoundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerC: " + msg);
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        byteBuf.writeBytes("你也好".getBytes(Charset.forName("utf-8")));
        /**
         * 要使用channel的write，
         * 因为Channel.write() :从 tail 到 head 调用每一个outbound 的 ChannelHandlerContext.write
         * 而ChannelHandlerContext.write() : 从当前的Context, 找到上一个outbound, 从后向前调用 write
         * 而在这里，InBoundHandlerC上面没有OutBoundHandler，所以就直接忽略所有OutBoundHandler
         */
        // 移除OutBoundHandlerC
        ctx.pipeline().remove("out_C");
        System.out.println("上个Handler处理结束时间："+ctx.channel().attr(AttributeKey.valueOf("endTime")).get());
        Thread.sleep(2000);
        ctx.channel().attr(AttributeKey.valueOf("endTime")).set(DateUtil.now());
        ctx.channel().writeAndFlush(byteBuf);
    }
}
