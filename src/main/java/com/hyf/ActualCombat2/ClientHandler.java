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
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端开始登录");
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserName("howinfun");
        packet.setPassword("123456");
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx,packet);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()){
                System.out.println("登录成功");
            }else{
                System.out.println("登录失败，"+loginResponsePacket.getReason());
            }
        }
    }
}
