package com.hyf.client.handler;

import com.hyf.protocol.response.LoginResponsePacket;
import com.hyf.server.session.Session;
import com.hyf.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.getSuccess()) {
            // 获取登录成功后的信息并打印
            String userId = loginResponsePacket.getUserId();
            Session session = new Session();
            session.setUserId(userId);
            System.out.println("["+loginResponsePacket.getUserName()+"]登录成功，userId 为："+userId);
            // 在客户端也需要绑定Session
            SessionUtil.bindSession(session,channelHandlerContext.channel());
        } else {
            System.out.println("["+loginResponsePacket.getUserName()+"]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }
}
