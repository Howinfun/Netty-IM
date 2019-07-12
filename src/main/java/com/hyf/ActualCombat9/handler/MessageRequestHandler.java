package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.TaskThreadPool;
import com.hyf.ActualCombat9.entity.Session;
import com.hyf.ActualCombat9.packet.MessageRequestPacket;
import com.hyf.ActualCombat9.packet.MessageResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        TaskThreadPool.INSTANCE.submit(()->{
            String msg = messageRequestPacket.getMessage();
            String toUserId = messageRequestPacket.getToUserId();
            Channel toChannel = SessionUtil.getChannel(toUserId);
            Session fromSession = SessionUtil.getSession(ctx.channel());

            // 发送消息到接收端
            MessageResponsePacket responsePacket = new MessageResponsePacket();
            if (toChannel != null && SessionUtil.isLogin(toChannel)){
                responsePacket.setSucess(true);
                responsePacket.setFromUserId(fromSession.getUserId());
                responsePacket.setFromUserName(fromSession.getUserName());
                responsePacket.setMessage(msg);
                toChannel.writeAndFlush(responsePacket);
            }else{
                responsePacket.setSucess(false);
                responsePacket.setMessage("对方不在线，发送失败");
                ctx.channel().writeAndFlush(responsePacket);
            }
        });
    }
}
