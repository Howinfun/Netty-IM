package com.hyf.ActualCombat8.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.hyf.ActualCombat8.entity.Session;
import com.hyf.ActualCombat8.packet.LoginRequestPacket;
import com.hyf.ActualCombat8.packet.LoginResponsePacket;
import com.hyf.ActualCombat8.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    /** 使用静态成员变量来统计，因为静态成员变量只属于类，所有类的实例都持有同一个静态成员变量
     *  注意：此静态成员变量的修饰词为public，因为要在服务端启动后，打印出来 */
    public static AtomicInteger count = new AtomicInteger(0);

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
            // 活跃连接数➕1
            LoginRequestHandler.count.incrementAndGet();
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
        System.out.println("channelInactive");
        // 通道断开时，判断客户端是否登录成功过，如果是的话解绑session，并且活跃连接数减一
        if (SessionUtil.isLogin(ctx.channel())){
            // 活跃连接数减1
            LoginRequestHandler.count.decrementAndGet();
            // 用户断线之后取消绑定
            SessionUtil.unBindSession(ctx.channel());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 判断IOException异常，暂时是处理客户端主动断开连接报的异常->IOException: 远程主机强迫关闭了一个现有的连接
        // 其他异常直接放行到下个处理
        if (cause instanceof IOException){
            System.out.println("客户端"+SessionUtil.getSession(ctx.channel()).getUserName()+"断开连接");
        }else {
            super.exceptionCaught(ctx,cause);
        }
    }

    private static boolean isValid(LoginRequestPacket loginRequestPacket){
        if ("123456".equals(loginRequestPacket.getPassword())){
            return true;
        }
        return false;
    }
}
