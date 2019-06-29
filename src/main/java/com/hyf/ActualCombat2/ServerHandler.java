package com.hyf.ActualCombat2;

import com.hyf.ActualCombat2.packet.LoginRequestPacket;
import com.hyf.ActualCombat2.packet.LoginResponsePacket;
import com.hyf.ActualCombat2.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf receive = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(receive);
        if (packet instanceof LoginRequestPacket){
            System.out.println("客户端开始登陆.....");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (valid(loginRequestPacket)){
                System.out.println("登录成功");
            }else{
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账户或密码不正确");
                System.out.println("登录失败：账户或密码不正确");
            }
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx,loginResponsePacket);
            ctx.channel().writeAndFlush(byteBuf);
        }

    }

    private boolean valid(LoginRequestPacket packet){
        if ("howinfun".equals(packet.getUserName()) && "123456".equals(packet.getPassword())){
            return true;
        }
        return false;
    }
}
