package com.hyf.ActualCombat3.handler;

import cn.hutool.core.date.DateUtil;
import com.hyf.ActualCombat3.PacketCodeC;
import com.hyf.ActualCombat3.packet.LoginRequestPacket;
import com.hyf.ActualCombat3.packet.LoginResponsePacket;
import com.hyf.ActualCombat3.packet.MessageResponsePacket;
import com.hyf.ActualCombat3.packet.Packet;
import com.hyf.ActualCombat3.utils.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserName("howinfun");
        packet.setPassword("123456");
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),packet);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.deCode(byteBuf);
        if (packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.getSuccess()){
                System.out.println(DateUtil.now()+" 登录成功");
                // 添加登录成功标识，启动线程让用户发送消息
                LoginUtils.markLogin(ctx.channel());
            }else {
                System.out.println(DateUtil.now()+" 登录失败："+loginResponsePacket.getMessage());
            }
        }else if (packet instanceof MessageResponsePacket){
            MessageResponsePacket responsePacket = (MessageResponsePacket) packet;
            String responseMsg = responsePacket.getMessage();
            System.out.println(DateUtil.now()+"收到服务端消息："+responseMsg);
        }
    }
}
