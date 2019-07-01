package com.hyf.TestPipelineAndChannelHandler;

import cn.hutool.core.date.DateUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.AttributeKey;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class OutBoundHandlerA extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundHandlerA: " + msg);
        System.out.println("上个Handler处理结束时间："+ctx.channel().attr(AttributeKey.valueOf("endTime")).get());
        Thread.sleep(1000);
        ctx.channel().attr(AttributeKey.valueOf("endTime")).set(DateUtil.now());
        super.write(ctx, msg, promise);
    }
}
