package com.hyf.server.handler;

import com.hyf.protocol.request.LoginRequestPacket;
import com.hyf.protocol.response.LoginResponsePacket;
import com.hyf.server.session.Session;
import com.hyf.utils.IDUtil;
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
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {

        LoginResponsePacket packet = new LoginResponsePacket();
        packet.setVersion(loginRequestPacket.getVersion());
        if(valid(loginRequestPacket)){
            packet.setSuccess(true);
            // 绑定用户的登录信息
            Session session = new Session();
            // 在服务端给用户生成随机id
            String userId = IDUtil.randomId();
            session.setUserId(userId);
            session.setUserName(loginRequestPacket.getUserName());
            SessionUtil.bindSession(session,channelHandlerContext.channel());
            // 返回登录成功信息
            packet.setUserId(userId);
            packet.setUserName(loginRequestPacket.getUserName());
            System.out.println("["+loginRequestPacket.getUserName()+"]登录成功");
        }else{
            // 返回登录失败信息
            packet.setSuccess(false);
            packet.setReason("用户信息校验失败");
            System.out.println("["+loginRequestPacket.getUserName()+"]登录失败");
        }
        channelHandlerContext.channel().writeAndFlush(packet);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        // 这里可以对登录信息进行验证（查询数据库）   现在直接返回true
        return true;
    }

    // 用户断线之后取消绑定
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }

}
