package com.hyf.ActualCombat3.handler;

import cn.hutool.core.date.DateUtil;
import com.hyf.ActualCombat3.packet.MessageResponsePacket;
import com.hyf.ActualCombat3.packet.Packet;
import com.hyf.ActualCombat3.PacketCodeC;
import com.hyf.ActualCombat3.packet.LoginRequestPacket;
import com.hyf.ActualCombat3.packet.LoginResponsePacket;
import com.hyf.ActualCombat3.packet.MessageRequestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf receive = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.deCode(receive);
        if (packet instanceof LoginRequestPacket){
            System.out.println(DateUtil.now()+" 客户端正在登录.....");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (isValid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                System.out.println(DateUtil.now()+" 登录成功");
            }else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setMessage("帐号或密码错误");
                System.out.println(DateUtil.now()+" 登录失败");
            }
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),loginResponsePacket);
            ctx.channel().writeAndFlush(byteBuf);

        }else if (packet instanceof MessageRequestPacket){
            MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
            String requestMsg = requestPacket.getMessage();
            System.out.println(DateUtil.now()+" 收到客户端消息："+requestMsg);
            // 反馈消息
            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setMessage("服务端回复【"+requestMsg+"】");
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),responsePacket);
            ctx.channel().writeAndFlush(byteBuf);
        }
    }

    private static boolean isValid(LoginRequestPacket loginRequestPacket){
        if ("howinfun".equals(loginRequestPacket.getUserName()) && "123456".equals(loginRequestPacket.getPassword())){
            return true;
        }
        return false;
    }
}
