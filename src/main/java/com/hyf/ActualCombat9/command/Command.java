package com.hyf.ActualCombat9.command;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public interface Command {
    /**
     * 登录
     */
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    /**
     * 发送消息
     */
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
    /**
     * 创建群聊
     */
    Byte CREATE_GROUP_REQUEST = 5;
    Byte CREATE_GROUP_RESPONSE = 6;
    /**
     * 登出
     */
    Byte LOGOUT_REQUEST = 7;
    Byte LOGOUT_RESPONSE = 8;
    /**
     * 加入群聊
     */
    Byte JOIN_GROUP_REQUEST = 9;
    Byte JOIN_GROUP_RESPONSE = 10;
    Byte JOIN_GROUP_NOTICE = 101;
    /**
     * 退出群聊
     */
    Byte QUIT_GROUP_REQUEST = 11;
    Byte QUIT_GROUP_RESPONSE = 12;
    /**
     * 获取群聊成员列表
     */
    Byte LIST_GROUP_MEMBERS_REQUEST = 13;
    Byte LIST_GROUP_MEMBERS_RESPONSE = 14;

    /**
     * 发送群消息
     */
    Byte GROUP_MESSAGE_REQUEST = 15;
    Byte GROUP_MESSAGE_RESPONSE = 16;
}
