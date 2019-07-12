package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.hyf.ActualCombat9.command.Command.CREATE_GROUP_RESPONSE;
import static com.hyf.ActualCombat9.command.Command.GROUP_MESSAGE_RESPONSE;
import static com.hyf.ActualCombat9.command.Command.JOIN_GROUP_NOTICE;
import static com.hyf.ActualCombat9.command.Command.JOIN_GROUP_RESPONSE;
import static com.hyf.ActualCombat9.command.Command.LIST_GROUP_MEMBERS_RESPONSE;
import static com.hyf.ActualCombat9.command.Command.LOGOUT_RESPONSE;
import static com.hyf.ActualCombat9.command.Command.MESSAGE_RESPONSE;
import static com.hyf.ActualCombat9.command.Command.QUIT_GROUP_RESPONSE;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/11
 */
@ChannelHandler.Sharable
public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMClientHandler INSTANCE = new IMClientHandler();
    private final Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap = new HashMap<>();

    private IMClientHandler(){
        // LoginResponseHandler不能合并到这里是因为AuthHandler跟随在它后面，而AuthHandler是所有请求都要通过的
        handlerMap.put(MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponseHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponseHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_NOTICE, JoinGroupNoticeHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponseHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponseHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponseHandler.INSTANCE);
        handlerMap.put(LOGOUT_RESPONSE, LogoutResponseHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx,msg);
    }
}
