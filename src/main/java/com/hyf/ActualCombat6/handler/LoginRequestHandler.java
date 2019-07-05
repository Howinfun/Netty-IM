package com.hyf.ActualCombat6.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.hyf.ActualCombat6.entity.Session;
import com.hyf.ActualCombat6.packet.LoginRequestPacket;
import com.hyf.ActualCombat6.packet.LoginResponsePacket;
import com.hyf.ActualCombat6.utils.SessionUtil;
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
        String userName = loginRequestPacket.getUserName();
        if (isValid(loginRequestPacket)){
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setMessage("["+userName+"]登录成功");
            System.out.println(DateUtil.now()+": ["+userName+"]登录成功");
            // 给chennel添加SESSION
            Session session = new Session();
            String userId = RandomUtil.randomString(4);
            loginResponsePacket.setUserId(userId);
            session.setUserId(userId);
            session.setUserName(loginRequestPacket.getUserName());
            SessionUtil.bindSession(ctx.channel(),session);
            // 给客户端响应
            ctx.channel().writeAndFlush(loginResponsePacket);
        }else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setMessage("帐号或密码错误");
            System.out.println(DateUtil.now()+": ["+userName+"]登录失败");
            // 登录响应，直接写packet，PacketEncoder会将帮我们编码
            ctx.channel().writeAndFlush(loginResponsePacket);
            // 登录失败及时关闭通道
            ctx.channel().close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 用户断线之后取消绑定
        SessionUtil.unBindSession(ctx.channel());
    }

    private static boolean isValid(LoginRequestPacket loginRequestPacket){
        if ("123456".equals(loginRequestPacket.getPassword())){
            return true;
        }
        return false;
    }
}
