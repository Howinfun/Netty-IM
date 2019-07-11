package com.hyf.ActualCombat9;

import com.hyf.ActualCombat9.command.Command;
import com.hyf.ActualCombat9.packet.CreateGroupRequestPacket;
import com.hyf.ActualCombat9.packet.CreateGroupResponsePacket;
import com.hyf.ActualCombat9.packet.GroupMessageRequestPacket;
import com.hyf.ActualCombat9.packet.GroupMessageResponsePacket;
import com.hyf.ActualCombat9.packet.JoinGroupNoticePacket;
import com.hyf.ActualCombat9.packet.JoinGroupRequestPacket;
import com.hyf.ActualCombat9.packet.JoinGroupResponsePacket;
import com.hyf.ActualCombat9.packet.ListGroupMembersRequestPacket;
import com.hyf.ActualCombat9.packet.ListGroupMembersResponsePacket;
import com.hyf.ActualCombat9.packet.LoginRequestPacket;
import com.hyf.ActualCombat9.packet.LoginResponsePacket;
import com.hyf.ActualCombat9.packet.LogoutRequestPacket;
import com.hyf.ActualCombat9.packet.LogoutResponsePacket;
import com.hyf.ActualCombat9.packet.MessageRequestPacket;
import com.hyf.ActualCombat9.packet.MessageResponsePacket;
import com.hyf.ActualCombat9.packet.Packet;
import com.hyf.ActualCombat9.packet.QuitGroupRequestPacket;
import com.hyf.ActualCombat9.packet.QuitGroupResponsePacket;
import com.hyf.ActualCombat9.serialize.JsonSerializer;
import com.hyf.ActualCombat9.serialize.Serializer;
import com.hyf.ActualCombat9.serialize.SerializerAlogrithm;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class PacketCodeC {

    private final Map<Byte,Object> serializerMap = new HashMap<>(1);
    private final Map<Byte,Class<? extends Packet>> packetMap = new HashMap<>(20);

    private PacketCodeC(){
        serializerMap.put(SerializerAlogrithm.JSON,new JsonSerializer());
        packetMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetMap.put(Command.JOIN_GROUP_NOTICE, JoinGroupNoticePacket.class);
        packetMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
    }
    /** 静态内部类做单例 */
    private static class SingleTon{
        static PacketCodeC packetCodeC = new PacketCodeC();
    }

    public static PacketCodeC INSTANCE = SingleTon.packetCodeC;

    public ByteBuf encode(ByteBuf byteBuf, Packet packet){
       // 魔数
       byteBuf.writeInt(Packet.MAGIC_NUMBER);
        // 版本号
       byteBuf.writeByte(Packet.VERSION);
        // 序列算法
       byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        // 指令
       byteBuf.writeByte(packet.getCommand());
        // 序列化
       byte[] data = Serializer.DEFAULT.serialize(packet);
        // 数据长度
       int dataLength = data.length;
       byteBuf.writeInt(dataLength);
       byteBuf.writeBytes(data);
       return byteBuf;
    }

    public Packet deCode(ByteBuf byteBuf){
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 获取序列化算法
        Byte serializerAlogrithm = byteBuf.readByte();
        Serializer serializer = (Serializer)serializerMap.get(serializerAlogrithm);
        // 获取指令
        Byte command = byteBuf.readByte();
        // 数据长度
        int dataLength = byteBuf.readInt();
        // 读取数据内容
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        // 反序列化
        Packet packet = serializer.deSerialize(data,packetMap.get(command));
        return packet;
    }
}
