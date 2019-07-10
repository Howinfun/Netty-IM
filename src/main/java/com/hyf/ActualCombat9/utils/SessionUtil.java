package com.hyf.ActualCombat9.utils;

import com.hyf.ActualCombat9.attribute.Attributes;
import com.hyf.ActualCombat9.entity.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Howinfun
 * @desc 服务端使用的
 * @date 2019/7/1
 */
public class SessionUtil {

    /** 客户端通道的映射 */
    private static final Map<String,Channel> channelMap = new ConcurrentHashMap<>(10);

    /** 群聊的映射 */
    private static final Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>(10);

    /**
     * 登录成功：绑定Session
     * @param channel
     */
    public static void bindSession(Channel channel, Session session){
        // 绑定Session
        channel.attr(Attributes.SESSION).set(session);
        // 将通道添加到映射中
        channelMap.put(session.getUserId(),channel);
    }

    /**
     * 判断是否已经登录：是否绑定Session
     * @param channel
     * @return
     */
    public static boolean isLogin(Channel channel){
        // 添加SESSION属性
        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 退出登录：解绑Session
     * @param channel
     * @return
     */
    public static void unBindSession(Channel channel){
        // 如果此通道已登录，则从映射中去掉，并去除SESSION属性
        if (isLogin(channel)){
            channelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 获取Session
     * @param channel
     * @return
     */
    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 获取通道
     * @param userId
     * @return
     */
    public static Channel getChannel(String userId){
        return channelMap.get(userId);
    }

    /**
     * 将群聊组缓存起来
     * @param groupId
     * @param channelGroup
     */
    public static void addChannelGroup(String groupId,ChannelGroup channelGroup){
        channelGroupMap.put(groupId,channelGroup);
    }

    /**
     * 根据群聊ID获取channelGroup
     * @param groupId
     * @return
     */
    public static ChannelGroup getChannelGroup(String groupId){
        return channelGroupMap.get(groupId);
    }
}
