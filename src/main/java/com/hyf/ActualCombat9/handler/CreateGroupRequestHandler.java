package com.hyf.ActualCombat9.handler;

import cn.hutool.core.util.RandomUtil;
import com.hyf.ActualCombat9.packet.CreateGroupRequestPacket;
import com.hyf.ActualCombat9.packet.CreateGroupResponsePacket;
import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/9
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        // 群聊人员ID
        List<String> userIds = msg.getUserId();
        // 获取群聊人员名字
        List<String> userName = new ArrayList<>();
        // 利用ChannelGroup的广播机制实现群发
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (String userId : userIds) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null){
                userName.add(SessionUtil.getSession(channel).getUserName());
                channelGroup.add(channel);
            }
        }
        // 最后记得添加上自己的channel,这样子就不用建群也要输入自己的ID了
        channelGroup.add(ctx.channel());
        String groupId = RandomUtil.randomString(4);
        // 将群聊存在缓存中
        SessionUtil.addChannelGroup(groupId,channelGroup);
        // 创建响应
        CreateGroupResponsePacket groupResponsePacket = new CreateGroupResponsePacket();
        groupResponsePacket.setGroupId(groupId);
        groupResponsePacket.setUserNames(userName);
        groupResponsePacket.setSuccess(true);
        channelGroup.writeAndFlush(groupResponsePacket);

        System.out.print("群创建成功，id 为[" + groupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + groupResponsePacket.getUserNames());
    }
}
