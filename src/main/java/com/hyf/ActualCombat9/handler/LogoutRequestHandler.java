package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.packet.LogoutRequestPacket;
import com.hyf.ActualCombat9.packet.LogoutResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        /**
         * 登出-> 响应客户端
         * 不能在这解绑Session，因为客户端那会关闭通道，到时候会到ChannelInactive，自然会解绑
         */
        /*SessionUtil.unBindSession(ctx.channel());
        ctx.channel().attr(Attributes.SESSION).set(null);
        LoginRequestHandler.count.decrementAndGet();*/
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
