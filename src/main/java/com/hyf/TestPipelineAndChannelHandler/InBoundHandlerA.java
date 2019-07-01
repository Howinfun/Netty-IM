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
public class InBoundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA: " + msg);
        // 移除InBoundHandlerB
        ctx.pipeline().remove("in_B");
        ctx.channel().attr(AttributeKey.newInstance("endTime")).set(DateUtil.now());
        super.channelRead(ctx, msg);
    }
}
