package com.hyf.TestPipelineAndChannelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.AttributeKey;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class OutBoundHandlerC extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundHandlerC: " + msg);
        System.out.println("上个Handler处理结束时间："+ctx.channel().attr(AttributeKey.valueOf("endTime")).get());
        //ctx.channel().attr(AttributeKey.valueOf("endTime")).set(DateUtil.now());
        super.write(ctx, msg, promise);
    }
}
