package com.hyf.ActualCombat4.handler;

import cn.hutool.core.date.DateUtil;
import com.hyf.ActualCombat4.packet.LoginRequestPacket;
import com.hyf.ActualCombat4.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(DateUtil.now()+" 客户端正在登录.....");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (isValid(loginRequestPacket)){
            loginResponsePacket.setSuccess(true);
            System.out.println(DateUtil.now()+" 登录成功");
        }else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setMessage("帐号或密码错误");
            System.out.println(DateUtil.now()+" 登录失败");
        }
        // 登录响应，直接写packet，PacketEncoder会将帮我们编码
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private static boolean isValid(LoginRequestPacket loginRequestPacket){
        if ("howinfun".equals(loginRequestPacket.getUserName()) && "123456".equals(loginRequestPacket.getPassword())){
            return true;
        }
        return false;
    }
}
