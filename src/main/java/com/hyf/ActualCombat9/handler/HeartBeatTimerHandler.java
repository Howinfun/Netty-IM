package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.packet.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/12
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final long PERIOD = 5;
    private Future future;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 如果通道断开则停止定时任务
        System.out.println("关掉定时任务");
        future.cancel(true);
        super.channelInactive(ctx);
    }

    private void sendHeartBeat(ChannelHandlerContext ctx){
        /*schedule只会执行一次
        future = ctx.executor().schedule(()->{
            // 因为不需要通过HeartBeatTimerHandler后面的的ChannelInBoundHandler和，所以可以直接使用ctx.writeAndFlush
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                sendHeartBeat(ctx);
            }
        },DELAY, TimeUnit.SECONDS);*/

        future =ctx.executor().scheduleAtFixedRate(()->{
            if (ctx.channel().isActive()) {
                // 因为不需要通过HeartBeatTimerHandler后面的的ChannelInBoundHandler和，所以可以直接使用ctx.writeAndFlush
                ctx.writeAndFlush(new HeartBeatRequestPacket());
            }
        },0,PERIOD,TimeUnit.SECONDS);

    }
}
