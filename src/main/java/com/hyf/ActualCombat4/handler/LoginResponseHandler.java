package com.hyf.ActualCombat4.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.hyf.ActualCombat4.packet.LoginRequestPacket;
import com.hyf.ActualCombat4.packet.LoginResponsePacket;
import com.hyf.ActualCombat4.utils.LoginUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("howinfun");
        loginRequestPacket.setPassword("123456");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.getSuccess()){
            System.out.println(DateUtil.now()+" 登录成功");
            // 添加登录成功标识，启动线程让用户发送消息
            LoginUtils.markLogin(ctx.channel());
        }else {
            System.out.println(DateUtil.now()+" 登录失败："+loginResponsePacket.getMessage());
        }
    }
}
