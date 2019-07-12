package com.hyf.ActualCombat9.handler.client;

import com.hyf.ActualCombat9.packet.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    private JoinGroupResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()){
            System.out.println("加入群["+msg.getGroupId()+"]成功");
        }else {
            System.err.println(msg.getMessage());
        }
    }
}
