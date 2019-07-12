package com.hyf.ActualCombat9.handler.client;

import com.hyf.ActualCombat9.packet.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    private CreateGroupResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()){
            System.out.println("群创建成功,id为["+msg.getGroupId()+"],群里面有：["+msg.getUserNames().toString()+"]");
        }else {
            System.out.println("群创建失败");
        }
        /*System.out.print("群创建成功，id 为[" + msg.getGroupId() + "], ");
        System.out.println("群里面有：" + msg.getUserNames());*/
    }
}
