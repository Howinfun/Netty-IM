package com.hyf.ActualCombat8.handler;

import cn.hutool.core.date.DateUtil;
import com.hyf.ActualCombat8.packet.LoginResponsePacket;
import com.hyf.ActualCombat8.utils.LoginUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserName("howinfun");
        loginRequestPacket.setPassword("123456");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.getSuccess()){
            System.out.println(DateUtil.now()+loginResponsePacket.getMessage()+" userId为"+loginResponsePacket.getUserId());
            // 添加登录成功标识，启动线程让用户发送消息
            LoginUtils.markLogin(ctx.channel());
            System.out.println("可以开始你的单聊了，快去撩妹子吧~");
        }else {
            System.out.println(DateUtil.now()+" 登录失败："+loginResponsePacket.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 判断IOException异常，暂时是处理客户端主动断开连接报的异常->IOException: 远程主机强迫关闭了一个现有的连接
        // 其他异常直接放行到下个处理
        if (cause instanceof IOException){
            System.out.println("服务端断开连接");
        }else {
            super.exceptionCaught(ctx,cause);
        }
    }
}
