package com.hyf.ActualCombat9.handler.server;

import com.hyf.ActualCombat9.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.hyf.ActualCombat9.command.Command.CREATE_GROUP_REQUEST;
import static com.hyf.ActualCombat9.command.Command.GROUP_MESSAGE_REQUEST;
import static com.hyf.ActualCombat9.command.Command.JOIN_GROUP_REQUEST;
import static com.hyf.ActualCombat9.command.Command.LIST_GROUP_MEMBERS_REQUEST;
import static com.hyf.ActualCombat9.command.Command.LOGOUT_REQUEST;
import static com.hyf.ActualCombat9.command.Command.MESSAGE_REQUEST;
import static com.hyf.ActualCombat9.command.Command.QUIT_GROUP_REQUEST;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
@ChannelHandler.Sharable
public class IMServerHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMServerHandler INSTANCE = new IMServerHandler();
    private final Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap = new HashMap<>();

    private IMServerHandler(){
        // LoginRequestHandler不能合并到这里是因为AuthHandler跟随在它后面，而AuthHandler是所有请求都要通过的
        handlerMap.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx,msg);
    }
}
