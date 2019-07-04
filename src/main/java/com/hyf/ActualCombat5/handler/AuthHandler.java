package com.hyf.ActualCombat5.handler;

import com.hyf.ActualCombat5.utils.LoginUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/4
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断客户端是否登录成功，如果登录成功则移除此handler，并继续执行下一个Handler,否则直接关闭channel
        if (!LoginUtils.isLogin(ctx.channel())){
            ctx.channel().close();
        }else{
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtils.isLogin(ctx.channel())){
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        }else {
            System.out.println("无登录认证，强制关闭连接");
        }
    }
}
