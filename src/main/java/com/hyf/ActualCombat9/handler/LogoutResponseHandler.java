package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.packet.LogoutResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        if (msg.isSuccess()){
            System.out.println("登出成功");
            ctx.channel().close();
        }else {
            System.out.println("登出失败，原因："+msg.getMsg());
        }
    }
}
