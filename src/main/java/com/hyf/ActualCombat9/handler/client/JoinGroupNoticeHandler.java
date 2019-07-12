package com.hyf.ActualCombat9.handler.client;

import com.hyf.ActualCombat9.packet.JoinGroupNoticePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/10
 */
@ChannelHandler.Sharable
public class JoinGroupNoticeHandler extends SimpleChannelInboundHandler<JoinGroupNoticePacket> {
    public static final JoinGroupNoticeHandler INSTANCE = new JoinGroupNoticeHandler();

    private JoinGroupNoticeHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupNoticePacket msg) throws Exception {
        if (msg.getOperate() == 1){
            System.out.println(msg.getSession().getUserName()+"加入群聊["+msg.getGroupId()+"]");
        }else if (msg.getOperate() == 2){
            System.out.println(msg.getSession().getUserName()+"退出群聊["+msg.getGroupId()+"]");
        }
    }
}
