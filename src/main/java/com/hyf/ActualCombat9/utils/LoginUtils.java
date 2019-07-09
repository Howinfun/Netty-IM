package com.hyf.ActualCombat9.utils;

import com.hyf.ActualCombat567.attribute.Attributes;
import io.netty.channel.Channel;

/**
 * @author Howinfun
 * @desc  客户端使用的
 * @date 2019/7/1
 */
public class LoginUtils {
    /**
     * 添加登录标识
     * @param channel
     */
    public static void markLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断是否已经登录
     * @param channel
     * @return
     */
    public static boolean isLogin(Channel channel){
        return channel.attr(Attributes.LOGIN).get() == null ? false : channel.attr(Attributes.LOGIN).get();
    }
}
