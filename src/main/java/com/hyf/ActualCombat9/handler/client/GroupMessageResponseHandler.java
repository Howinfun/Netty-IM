package com.hyf.ActualCombat9.handler.client;

import com.hyf.ActualCombat9.entity.Session;
import com.hyf.ActualCombat9.packet.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
@ChannelHandler.Sharable
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();

    private GroupMessageResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        if (msg.isSuccess()){
            String fromGroupId = msg.getFromGroupId();
            String message = msg.getMessage();
            Session fromUser = msg.getFromUser();
            System.out.println("收到来自群["+fromGroupId+"]中"+fromUser+"发来的消息："+message);
        }else {
            System.err.println(msg.getMessage());
        }
    }
}
