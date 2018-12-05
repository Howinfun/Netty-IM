package com.hyf.utils;

import com.hyf.attribute.Attributes;
import com.hyf.server.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public class SessionUtil {

    private static final Map<String,Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session,Channel channel){
        // 将用户对应的channel记录起来
        userIdChannelMap.put(session.getUserId(),channel);
        // 给已登录的channel添加SESSION标识，可用判断是否已登录
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
