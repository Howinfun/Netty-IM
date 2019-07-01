package com.hyf.TestPipelineAndChannelHandler;

import cn.hutool.core.date.DateUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class InBoundHandlerB extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerB: " + msg);
        System.out.println("上个Handler处理结束时间："+ctx.channel().attr(AttributeKey.valueOf("endTime")).get());
        Thread.sleep(1000);
        ctx.channel().attr(AttributeKey.valueOf("endTime")).set(DateUtil.now());
        super.channelRead(ctx, msg);
    }
}
