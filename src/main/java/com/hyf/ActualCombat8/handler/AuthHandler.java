package com.hyf.ActualCombat8.handler;

import com.hyf.ActualCombat8.utils.SessionUtil;
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
        if (!SessionUtil.isLogin(ctx.channel())){
            ctx.channel().close();
        }else{
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }

    }

    /**
     * 注意：除了主动调用，客户端断开连接，每个handler都会被移除掉，所以也会被调用一次
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.isLogin(ctx.channel())){
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        }else {
            System.out.println("无登录认证，强制关闭连接");
            ctx.channel().close();
        }
    }
}
