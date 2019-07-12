package com.hyf.ActualCombat9.handler.client;

import com.hyf.ActualCombat9.packet.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
@ChannelHandler.Sharable
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    public static final ListGroupMembersResponseHandler INSTANCE = new ListGroupMembersResponseHandler();

    private ListGroupMembersResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket msg) throws Exception {
        if (msg.isSuccess()){
            System.out.println("群["+msg.getGroupId()+"]的人包括："+msg.getSessionList());
        }else {
            System.out.println(msg.getReason());
        }
    }
}
